#!groovy​

// FULL_BUILD -> true/false build parameter to define if we need to run the entire stack for lab purpose only
final FULL_BUILD = true
// HOST_PROVISION -> server to run ansible based on provision/inventory.ini
//final HOST_PROVISION = params.HOST_PROVISION
// limit: 'app_server' injecting by hardcoded
final HOST_PROVISION = '172.31.42.149'

 


final GIT_URL = 'https://github.com/Djrohith/sayhello.git'
final NEXUS_URL = '54.213.94.195:8081'

stage('Build') {
    node {
        git GIT_URL
        withEnv(["PATH+MAVEN=${tool 'm3'}/bin"]) {
            if(FULL_BUILD) {
                mvnHome = tool 'm3'
                sh "echo 'inside build'"
                
                 if (isUnix()) {
                     sh "mvn -B versions:set -DnewVersion=0.0.2-${BUILD_NUMBER}"
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      } else {
         bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
      }
                
            }
        }
    }
}

if(FULL_BUILD) {
    stage('Unit Tests') {   
        node {
            withEnv(["PATH+MAVEN=${tool 'm3'}/bin"]) {
                sh "mvn -B clean test"
                stash name: "unit_tests", includes: "target/surefire-reports/**"
            }
        }
    }
}

if(FULL_BUILD) {
    stage('Integration Tests') {
        node {
            withEnv(["PATH+MAVEN=${tool 'm3'}/bin"]) {
                sh "mvn -B clean verify -Dsurefire.skip=true"
                stash name: 'it_tests', includes: 'target/failsafe-reports/**'
            }
        }
    }
}

if(FULL_BUILD) {
    stage('Static Analysis') {
        node {
            withEnv(["PATH+MAVEN=${tool 'm3'}/bin"]) {
                withSonarQubeEnv('sonar'){
                    unstash 'it_tests'
                    unstash 'unit_tests'
                    sh 'mvn sonar:sonar -DskipTests'
                }
            }
        }
    }
}
/**
if(FULL_BUILD) {
    stage('Approval') {
        timeout(time:3, unit:'DAYS') {
            input 'Do I have your approval for deployment?'
        }
    }
}**/


if(FULL_BUILD) {
    stage('Artifact Upload') {
        node {
            sh 'cd target | ls'
            
            // TODO remove below line once sonar is up         
            //unstash 'unit_tests'
         
         
            nexusArtifactUploader artifacts: [[groupId: 'say-hello-versions', 
                                               artifactId: 'say-hello', classifier: '',
                                               file: 'target/sayhello-0.0.2-${BUILD_NUMBER}.jar', type: 'jar']],
                credentialsId: 'e19a9825-a8d9-4762-be76-4de575a16141', 
                groupId: 'com.cg.demo', nexusUrl: "${NEXUS_URL}", nexusVersion: 'nexus3', 
                protocol: 'http', repository: 'say-hello', version: '0.0.2-${BUILD_NUMBER}'
         
             
        }
    }
}


stage('Deploy') {
    node {
        
        

       // http://34.221.40.216:8081/repository/demoapp-rele/br/com/meetup/ansible/soccer-stats/0.0.2-3/soccer-stats-0.0.2-3.war 
                           // http://52.88.118.15:8081/repository/say-hello/com/cg/demo/say-hello/0.0.2-8/say-hello-0.0.2-8.jar
                            
     
        def artifactUrl = "http://${NEXUS_URL}/repository/say-hello/com/cg/demo/say-hello/0.0.2-${BUILD_NUMBER}/say-hello-0.0.2-${BUILD_NUMBER}.jar"

        withEnv(["ARTIFACT_URL=${artifactUrl}", "APP_NAME='say-hello'"]) {
            echo "The URL is ${env.ARTIFACT_URL} and the app name is ${env.APP_NAME}"

            // install galaxy roles
            //sh "ansible-galaxy install -vvv -r provision/requirements.yml -p provision/roles/"    
            sh "which ansible"
         
            //sh "ansible -m ping app_server"
           // sh "ansible-playbook  provision/playbook.yml --extra-vars \" variable_host='localhost' ARTIFACT_URL=${artifactUrl} APP_NAME='soccer-demo' \" -u ec2-user --private-key=/home/ec2-user/node1.pem "       
            
         
         ansiblePlaybook colorized: true, 
            credentialsId: 'playbook',
            //limit: "${HOST_PROVISION}",
            installation: 'ansible',
            inventory: 'provision/inventory.ini', 
            playbook: 'provision/playbook.yml'
            //sudo: true,
            //sudoUser: 'ansible'
               
         /**
            ansiblePlaybook become: true, colorized: true, 
                credentialsId: 'ansible', disableHostKeyChecking: true,              
                //extras: 'ARTIFACT_URL="${artifactUrl}" APP_NAME=soccer-demo',
                inventory: 'provision/inventory.ini',
                playbook: 'provision/playbook.yml',
                sudoUser: 'ec2-user' 
                
            /**ansiblePlaybook colorized: true, 
            credentialsId: 'ssh-jenkins',
            limit: "${HOST_PROVISION}",
            installation: 'ansible',
            inventory: 'provision/inventory.ini', 
            playbook: 'provision/playbook.yml', 
            sudo: true,
            sudoUser: 'jenkins' **/
        }
    } 
}
