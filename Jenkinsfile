node {
    stage("pull") {
        git credentialsId: '62de294a-ace3-48c5-8dec-1bc663d3d80c', url: 'git@gitbud.epam.com:Dmytro_Baziaka/Sport_Betting_application.git'
    }

    stage("build") {
        dir('sports-betting-application') {
            sh 'mvn clean install'
        }

    }

    stage('publish') {
        dir('sports-betting-application/sports-betting-application-app/target') {
            version = "sba-" + System.currentTimeMillis();
            nexusPublisher nexusInstanceId: 'awsNexus', nexusRepositoryId: 'releases',
            packages: [[$class: 'MavenPackage',
            mavenAssetList: [[classifier: '', extension: '', filePath: 'sports-betting-application-jar-with-dependencies.jar']],
            mavenCoordinate: [artifactId: 'sports-betting-application', groupId: 'com.epam.traning.sportbetting', packaging: 'jar', version: "$version"]]]
        }
    }
}