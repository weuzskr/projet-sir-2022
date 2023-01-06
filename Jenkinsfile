pipeline {
  agent any
  tools {
    maven '3.6.3'
         }

      stages {
        stage('Source') {
          steps{ git branch: 'main', url: 'https://github.com/weuzskr/projet-sir-2022.git' }
                         }

      stage('Build') {
          steps { bat 'mvn package' }
                       }
      stage('SonarQube Analysis'){
          steps{ bat 'mvn sonar:sonar' }
              /* stage ('Approve Deployment') {
                            input {
                                message 'Do you want to proceed for deployment?'
                            }
                            steps{
                                bat 'echo "Deploying into server-G-4"'
                            }
                        } */
                         stage('DockerBuild') {
                                    steps {
                                        // Build the Docker image
                                        bat 'docker build -t projetsir2022/projet-sir:groupe-4  .'
                                    }
                                }
                            stage('dockertag') {
                                     steps {
                                                               // Build the Docker image
                                       bat 'docker tag projetsir2022/projet-sir:groupe-4 projetsir2022/projet2022'
                                              }
                                            }
                             stage('Push') {

                                                       /*  steps {
                                                           withDockerRegistry([credentialsId: "groupe-4" ,url:"" ]){
                                                           bat 'docker push projetsir2022/projet-sir:groupe-4'
                                                           }
                                                         }*/
                                                       }

          }
            }
        }
