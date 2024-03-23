package Soots;

import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.model.Method;
import sootup.core.model.SootMethod;
import sootup.core.signatures.MethodSignature;
import sootup.java.bytecode.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.core.JavaSootClass;
import sootup.java.core.JavaSootMethod;
import sootup.java.core.types.JavaClassType;
import sootup.java.core.views.JavaView;
import sootup.java.sourcecode.inputlocation.JavaSourcePathAnalysisInputLocation;

import java.util.Collections;
import java.util.Optional;

public class SootClass {

    public static void main(String[] args) {

        AnalysisInputLocation inputLocation =
                new JavaClassPathAnalysisInputLocation("target/classes/CollectionsProcessing");

        JavaView view = new JavaView(inputLocation);


    //  This retrieves the classType of the ListProcessor class
        JavaClassType classType =
                view.getIdentifierFactory().getClassType("CollectionsProcessing.ListProcessor");

        // classType is empty and contains no info about ListProcessor class

        MethodSignature methodSignature = view.getIdentifierFactory()
                .getMethodSignature(
                classType,
                "binarySearch",
                "Integer",
                Collections.singletonList("java.lang.String[]"));

        Optional<JavaSootMethod> opt = view.getMethod(methodSignature);

        if (opt.isPresent()) {
            SootMethod method = opt.get();
            System.out.println(method.getBody().getStmts());
        } else {
            System.out.println("No methods found");
        }
    }
}
