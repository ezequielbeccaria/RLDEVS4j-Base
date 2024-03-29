# RLDEVS4j-Base
Library for the implementation of reinforcement learning environments using a DEVS simulation engine (DEVSJAVA).

# Dependencies
    <dependencies>
            <dependency>
                <groupId>model</groupId>
                <artifactId>DEVS-Suite-facade</artifactId>
                <version>1.0</version>
            </dependency>
            <!--CPU-->
            <dependency>
                <groupId>org.nd4j</groupId>
                <artifactId>nd4j-native</artifactId>
                <version>${deeplearning4j.version}</version>
            </dependency>
            <dependency>
                <groupId>org.nd4j</groupId>
                <artifactId>nd4j-native</artifactId>
                <version>${deeplearning4j.version}</version>
                <classifier>linux-x86_64-avx2</classifier>
            </dependency>
            <!--END CPU-->
    
            <dependency>
                <groupId>org.nd4j</groupId>
                <artifactId>nd4j-api</artifactId>
                <version>${deeplearning4j.version}</version>
            </dependency>
            <dependency>
                <groupId>org.deeplearning4j</groupId>
                <artifactId>deeplearning4j-core</artifactId>
                <version>${deeplearning4j.version}</version>
            </dependency>
            <dependency>
                <groupId>org.deeplearning4j</groupId>
                <artifactId>deeplearning4j-ui</artifactId>
                <version>${deeplearning4j.version}</version>
            </dependency>
            <dependency>
                <groupId>org.freemarker</groupId>
                <artifactId>freemarker</artifactId>
                <version>2.3.29</version>
            </dependency>
            <dependency>
                <groupId>org.apache.commons</groupId>
                <artifactId>commons-collections4</artifactId>
                <version>4.2</version>
                <type>jar</type>
            </dependency>
            <!-- Plotting dependencies -->
            <dependency>
                <groupId>com.github.yannrichet</groupId>
                <artifactId>JMathPlot</artifactId>
                <version>1.0.1</version>
                <type>jar</type>
            </dependency>
            <!-- Logging dependencies -->
            <dependency>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-api</artifactId>
                <version>1.7.21</version>
            </dependency>
            <dependency>
                <groupId>ch.qos.logback</groupId>
                <artifactId>logback-classic</artifactId>
                <version>1.2.3</version>
            </dependency>
            <!--CSV utils-->
            <dependency>
                <groupId>com.opencsv</groupId>
                <artifactId>opencsv</artifactId>
                <version>4.6</version>
            </dependency>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-api</artifactId>
                <version>5.3.1</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-params</artifactId>
                <version>5.3.1</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>5.3.1</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <maven.compiler.source>1.8</maven.compiler.source>
            <maven.compiler.target>1.8</maven.compiler.target>
            <deeplearning4j.version>1.0.0-beta6</deeplearning4j.version>
        </properties>

DEVS-Suite-Facade dependency can be found in this [repository](https://github.com/ezequielbeccaria/Devs-Suite-Facade).

# Usage
![Class_diagram](/uml/class_diagram.png)
