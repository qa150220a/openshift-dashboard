node {

    checkout scm

    env.DOCKER_API_VERSION="1.23"
    
    sh "git rev-parse --short HEAD > commit-id"

    tag = readFile('commit-id').replace("\n", "").replace("\r", "")
    appName = "dashboard-ds"
    registryHost = "127.0.0.1:30400/"
    imageName = "${registryHost}${appName}:${tag}"
    env.BUILDIMG=imageName
    env.BUILD_TAG=tag

    stage ("Compile") {
        withMaven(
        // Maven installation declared in the Jenkins "Global Tool Configuration"
        maven: 'maven-3.6.3',
        // Maven settings.xml file defined with the Jenkins Config File Provider Plugin
        // We recommend to define Maven settings.xml globally at the folder level using 
        // navigating to the folder configuration in the section "Pipeline Maven Configuration / Override global Maven configuration"
        // or globally to the entire master navigating to  "Manage Jenkins / Global Tools Configuration"
        // mavenSettingsConfig: 'my-maven-settings') {
        ) {

      // Run the maven build
            sh "mvn package"
        }
    }

    stage ("Build") {
        sh "docker build -t ${imageName} ."
    }
    
    stage ("Push") {
        sh "docker push ${imageName}"
    }

    stage ("Deploy") {
        kubernetesDeploy configs: "k8s/*.yaml", kubeconfigId: 'kenzan_kubeconfig'
    }

}
