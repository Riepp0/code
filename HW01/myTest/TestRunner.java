package myTest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestSuite.class);
        for (Failure failure : result.getFailures())
            System.out.println(failure.toString());
        System.out.print("????????????????????????????\n");
        System.out.println("????? Success? " + result.wasSuccessful() + "!!!" + " ?????");
        System.out.print("????????????????????????????\n");
        System.out.println("Total tests " + result.getRunCount() + " in " + result.getRunCount() + " ms");
        System.out.println("End test ListAdapter and MapAdapter");

    }
}