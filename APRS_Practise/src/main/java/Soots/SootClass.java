package Soots;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import org.eclipse.jdt.core.dom.NullLiteral;
import sootup.core.cache.provider.LRUCacheProvider;
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
import spoon.compiler.builder.Options;

import java.util.Collections;
import java.util.Optional;

public class SootClass {

    public static void main(String[] args) {


        AnalysisInputLocation inputLocation =
                new JavaClassPathAnalysisInputLocation("target/classes/CollectionsProcessing");
//                new JavaSourcePathAnalysisInputLocation("src/main/java/LinkedList");

        JavaView view = new JavaView(inputLocation);
//        JavaView view = new JavaView(Collections.singletonList(inputLocation), new LRUCacheProvider(50));


    //  This retrieves the classType of the ListProcessor class
        JavaClassType classType =
                view.getIdentifierFactory().getClassType("CollectionsProcessing.ListProcessor");


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
