//package Spoons;
//import spoon.Launcher;
//import spoon.SpoonAPI;
//import spoon.reflect.CtModel;
//import spoon.reflect.declaration.CtClass;
//import spoon.reflect.declaration.CtMethod;
//
//public class SpoonClass {
//    public static void main(String[] args) {
//        System.out.println("Running main analysis...");
//        SpoonAPI launcher = new Launcher();
//        String srcPath = "src/main/java/CollectionsProcessing/ListProcessor.java";
//        launcher.addInputResource(srcPath);
//        launcher.buildModel();
//        CtModel model = launcher.getModel();
//
//        System.out.println("Package: " + model.getRootPackage().getPackage("CollectionsProcessing"));
//
//        for (Object o : model.getRootPackage().getElements(new spoon.reflect.visitor.filter.TypeFilter<>(CtClass.class))) {
//            CtClass<?> ctClass = (CtClass<?>) o;
//            System.out.println("Class: " + ctClass.getQualifiedName());
//
//            for (CtMethod<?> ctMethod : ctClass.getMethods()) {
//                System.out.println("Method: " + ctMethod.prettyprint());
//            }
//        }
//    }
//}
