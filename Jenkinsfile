pipeline{
   agent any
   tools{
       maven '3.8.6'
   }
   stages{
      stage("source"){
          steps{
             git branch: 'master' ,url:'https://github.com/tallahmad047/projet-sir-2022-main.git'
        }
      }
       stage ('Build') {
                  steps{
                      bat 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install'
                  }
              }

              stage ('SonarQube Analysis') {
                  steps{
                      bat 'mvn sonar:sonar'
                  }
              }

              stage ('Approve Deployment') {
                  input {
                      message 'Do you want to proceed for deployment?'
                  }
                  steps{
                      bat 'echo "Deploying into Server dev."'
                  }
              }
          } // stages

          post {
              aborted {
                  echo "Sending message to Agent"
              } // aborted

              failure {
                  echo "Sending message to Agent"
              } // failure

              success {
                  echo "Sending message to Agent"
              } // success
          } // post
   }
