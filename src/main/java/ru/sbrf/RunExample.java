package ru.sbrf;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sbrf.implementations.CacheableProxy;
import ru.sbrf.implementations.CalculatorImpl;
import ru.sbrf.interfaces.Calculator;
import ru.sbrf.repos.DatabaseRepository;

import java.lang.reflect.Proxy;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RunExample {

    private final CalculatorImpl calculator;
    private final DatabaseRepository databaseRepository;

    public void run() {
        ClassLoader cl = CalculatorImpl.class.getClassLoader();
        Calculator cachedCalculator = (Calculator) Proxy.newProxyInstance(cl, new Class[] {Calculator.class},
                new CacheableProxy(databaseRepository, calculator));
        List<Integer> fibonachi = cachedCalculator.fibonachi(5);
        fibonachi.forEach(System.out::println);
    }
}
