package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestSuite.class);
        for (Failure failure : result.getFailures())
            System.out.println(failure.toString());
        System.out.println("????????????????????????????");
        System.out.println("????? Success? " + result.wasSuccessful() + "!!!" + " ?????");
        System.out.println("????????????????????????????");
        System.out.println("Totale test effettuati " + result.getRunCount() + " in " + result.getRunCount() + " ms");
        System.out.println("Fine dei test di ListAdapter e MapAdapter");
        System.out.println("Powered by Michele Rieppi - Ingegneria Informatica @Padova - 2021");
    }
}