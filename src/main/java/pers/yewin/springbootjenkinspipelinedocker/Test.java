package pers.yewin.springbootjenkinspipelinedocker;

/**
 * @author: Ye Win
 * @created: 19/02/2023
 * @project: spring-boot-jpa-docker-jenkins-pipeline
 * @package: PACKAGE_NAME
 */

public class Test {

    private static final int MAX_BOUND = 100;
    private static final int MIN_BOUND = 100;

    public static void main(String[] args) {
        generate(78, "22.99.22");
    }
    
    private static String generate(int buildNo, String previousVersion){
        int patchIndex = previousVersion.lastIndexOf(".");

        int majorIndex = previousVersion.indexOf(".");

        int patch = Integer.parseInt(previousVersion.substring(patchIndex+1));
        int minor = Integer.parseInt(previousVersion.substring(majorIndex+1, patchIndex));
        int major = Integer.parseInt(previousVersion.substring(0, majorIndex));

        patch = patch + buildNo;
        
        if(patch==100) {
            minor = minor + 1;
            patch = 0;
        }

        if(minor == 100) {
            major = major + 1;
            minor = 0;
        }



        return null;
    } 
}
