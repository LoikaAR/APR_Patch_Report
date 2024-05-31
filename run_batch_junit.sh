#!/bin/bash

dir=$(pwd)
sampleLimit=5
projectsDir=$dir/IntroClassJava/dataset

for project in "$projectsDir"/*; do
	if [[ -d "$project" ]]; then
		projectRepo=$projectDir/$project
		sampleIdx=0
		for versionRepo in "$projectRepo"/*; do
		
			for subVersionDir in "$versionRepo"/*; do
				whiteBoxTestPath=$(find $subVersionDir/src/test/java/introclassJava/ -maxdepth 1 -name "*White*")
				whiteBoxTestFull=$(basename $whiteBoxTestPath)
				whiteBoxTest=${whiteBoxTestFull%.java*}
				
				blackBoxTestPath=$(find $subVersionDir/src/test/java/introclassJava/ -maxdepth 1 -name "*Black*") 
				blackBoxTest=$(basename $blackBoxTestPath)
				
				
				cd $subVersionDir
				mvn test
				
				#/usr/lib/jvm/java-17-openjdk-amd64/bin/java -Dvisualvm.id=218098654566428 -ea -Didea.test.cyclic.buffer.size=1048576 -javaagent:/home/arseni/idea-IC-233.14475.28/lib/idea_rt.jar=43383:/home/arseni/idea-IC-233.14475.28/bin -Dfile.encoding=UTF-8 -classpath /home/arseni/idea-IC-233.14475.28/lib/idea_rt.jar:/home/arseni/idea-IC-233.14475.28/plugins/junit/lib/junit5-rt.jar:/home/arseni/idea-IC-233.14475.28/plugins/junit/lib/junit-rt.jar:"$subVersionDir"/target/test-classes:"$subVersionDir"/target/classes:/home/arseni/.m2/repository/junit/junit/4.11/junit-4.11.jar:/home/arseni/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit4 introclassJava.$whiteBoxTest
				
				#/usr/lib/jvm/java-17-openjdk-amd64/bin/java -Dvisualvm.id=218098654566428 -ea -Didea.test.cyclic.buffer.size=1048576 -javaagent:/home/arseni/idea-IC-233.14475.28/lib/idea_rt.jar=43383:/home/arseni/idea-IC-233.14475.28/bin -Dfile.encoding=UTF-8 -classpath /home/arseni/idea-IC-233.14475.28/lib/idea_rt.jar:/home/arseni/idea-IC-233.14475.28/plugins/junit/lib/junit5-rt.jar:/home/arseni/idea-IC-233.14475.28/plugins/junit/lib/junit-rt.jar:$subVersionDir/target/test-classes:$subVersionDir/target/classes:/home/arseni/.m2/repository/junit/junit/4.11/junit-4.11.jar:/home/arseni/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit4 introclassJava.$blackBoxTest
			
			done	
		
			sampleIdx=$((sampleIdx+1))
			echo $sampleIdx
			if [ $sampleIdx -ge $sampleLimit ]; then
				break
			fi
		done
	fi
done
