package ru.sbrf.implementations;

import lombok.RequiredArgsConstructor;
import ru.sbrf.repos.DatabaseRepository;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class CacheableProxy implements InvocationHandler {

    private final DatabaseRepository databaseRepository;
    private final Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int arg = (int) args[0];
        Integer[] result = databaseRepository.selectWhereNumber(arg);
        if (result.length > 0) {
            System.out.println("Берем результат из кеша");
            return Arrays.asList(result);
        }
        System.out.println("Считаем с нуля");
        List<Integer> freshResult = (List<Integer>) method.invoke(target, args);
        int[] array = freshResult.stream().mapToInt(i -> i).toArray();
        Integer[] arrayToSave = Arrays.stream(array).boxed().toArray(Integer[]::new);
        databaseRepository.insert(arg, arrayToSave);
        return freshResult;
    }
}
