#!/usr/bin/env bash

dep_core_framework=$HOME/.m2/repository/org/apache/solr/solr-solrj/4.10.3/solr-solrj-4.10.3.jar:$HOME/.m2/repository/commons-io/commons-io/2.4/commons-io-2.4.jar:$HOME/.m2/repository/org/apache/httpcomponents/httpclient/4.3.1/httpclient-4.3.1.jar:$HOME/.m2/repository/org/apache/httpcomponents/httpcore/4.3/httpcore-4.3.jar:$HOME/.m2/repository/org/apache/httpcomponents/httpmime/4.3.1/httpmime-4.3.1.jar:$HOME/.m2/repository/org/apache/zookeeper/zookeeper/3.4.6/zookeeper-3.4.6.jar:$HOME/.m2/repository/org/noggit/noggit/0.5/noggit-0.5.jar:$HOME/.m2/repository/org/slf4j/slf4j-api/1.6.1/slf4j-api-1.6.1.jar:$HOME/.m2/repository/org/apache/solr/solr-core/4.10.3/solr-core-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-analyzers-common/4.10.3/lucene-analyzers-common-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-analyzers-kuromoji/4.10.3/lucene-analyzers-kuromoji-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-analyzers-phonetic/4.10.3/lucene-analyzers-phonetic-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-codecs/4.10.3/lucene-codecs-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-core/4.10.3/lucene-core-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-expressions/4.10.3/lucene-expressions-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-grouping/4.10.3/lucene-grouping-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-highlighter/4.10.3/lucene-highlighter-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-join/4.10.3/lucene-join-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-memory/4.10.3/lucene-memory-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-misc/4.10.3/lucene-misc-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-queries/4.10.3/lucene-queries-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-queryparser/4.10.3/lucene-queryparser-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-spatial/4.10.3/lucene-spatial-4.10.3.jar:$HOME/.m2/repository/org/apache/lucene/lucene-suggest/4.10.3/lucene-suggest-4.10.3.jar:$HOME/.m2/repository/com/carrotsearch/hppc/0.5.2/hppc-0.5.2.jar:$HOME/.m2/repository/com/google/guava/guava/12.0/guava-12.0.jar:$HOME/.m2/repository/com/google/protobuf/protobuf-java/2.5.0/protobuf-java-2.5.0.jar:$HOME/.m2/repository/com/googlecode/concurrentlinkedhashmap/concurrentlinkedhashmap-lru/1.2/concurrentlinkedhashmap-lru-1.2.jar:$HOME/.m2/repository/com/spatial4j/spatial4j/0.4.1/spatial4j-0.4.1.jar:$HOME/.m2/repository/commons-cli/commons-cli/1.1/commons-cli-1.1.jar:$HOME/.m2/repository/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar:$HOME/.m2/repository/commons-fileupload/commons-fileupload/1.2.2/commons-fileupload-1.2.2.jar:$HOME/.m2/repository/commons-lang/commons-lang/2.6/commons-lang-2.6.jar:$HOME/.m2/repository/dom4j/dom4j/1.6.1/dom4j-1.6.1.jar:$HOME/.m2/repository/log4j/log4j/1.2.12/log4j-1.2.12.jar:$HOME/.m2/repository/org/antlr/antlr-runtime/3.0/antlr-runtime-3.0.jar:$HOME/.m2/repository/org/apache/hadoop/hadoop-annotations/2.2.0/hadoop-annotations-2.2.0.jar:$HOME/.m2/repository/org/apache/hadoop/hadoop-auth/2.2.0/hadoop-auth-2.2.0.jar:$HOME/.m2/repository/org/apache/hadoop/hadoop-common/2.2.0/hadoop-common-2.2.0.jar:$HOME/.m2/repository/org/apache/hadoop/hadoop-hdfs/2.2.0/hadoop-hdfs-2.2.0.jar:$HOME/.m2/repository/org/ow2/asm/asm/4.1/asm-4.1.jar:$HOME/.m2/repository/org/ow2/asm/asm-commons/4.1/asm-commons-4.1.jar:$HOME/.m2/repository/org/restlet/jee/org.restlet/2.1.1/org.restlet-2.1.1.jar:$HOME/.m2/repository/org/restlet/jee/org.restlet.ext.servlet/2.1.1/org.restlet.ext.servlet-2.1.1.jar:$HOME/.m2/repository/org/mvel/mvel2/2.2.1.Final/mvel2-2.2.1.Final.jar:$HOME/.m2/repository/org/broadleafcommerce/broadleaf-common/4.0.5-GA/broadleaf-common-4.0.5-GA.jar:$HOME/.m2/repository/org/hibernate/hibernate-core/4.1.11.Final/hibernate-core-4.1.11.Final.jar:$HOME/.m2/repository/antlr/antlr/2.7.7/antlr-2.7.7.jar:$HOME/.m2/repository/org/jboss/logging/jboss-logging/3.1.0.GA/jboss-logging-3.1.0.GA.jar:$HOME/.m2/repository/org/jboss/spec/javax/transaction/jboss-transaction-api_1.1_spec/1.0.0.Final/jboss-transaction-api_1.1_spec-1.0.0.Final.jar:$HOME/.m2/repository/org/hibernate/common/hibernate-commons-annotations/4.0.1.Final/hibernate-commons-annotations-4.0.1.Final.jar:$HOME/.m2/repository/org/hibernate/hibernate-entitymanager/4.1.11.Final/hibernate-entitymanager-4.1.11.Final.jar:$HOME/.m2/repository/org/springframework/security/spring-security-web/3.2.6.RELEASE/spring-security-web-3.2.6.RELEASE.jar:$HOME/.m2/repository/aopalliance/aopalliance/1.0/aopalliance-1.0.jar:$HOME/.m2/repository/org/hibernate/hibernate-envers/4.1.11.Final/hibernate-envers-4.1.11.Final.jar:$HOME/.m2/repository/org/slf4j/jcl-over-slf4j/1.6.1/jcl-over-slf4j-1.6.1.jar:$HOME/.m2/repository/org/slf4j/slf4j-log4j12/1.6.1/slf4j-log4j12-1.6.1.jar:$HOME/.m2/repository/javax/mail/mail/1.4.1/mail-1.4.1.jar:$HOME/.m2/repository/javax/activation/activation/1.1/activation-1.1.jar:$HOME/.m2/repository/org/springframework/spring-jms/4.1.6.RELEASE/spring-jms-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/spring-aop/4.1.6.RELEASE/spring-aop-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/spring-messaging/4.1.6.RELEASE/spring-messaging-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/spring-tx/4.1.6.RELEASE/spring-tx-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/apache/velocity/velocity-tools/2.0/velocity-tools-2.0.jar:$HOME/.m2/repository/commons-beanutils/commons-beanutils/1.8.3/commons-beanutils-1.8.3.jar:$HOME/.m2/repository/commons-chain/commons-chain/1.1/commons-chain-1.1.jar:$HOME/.m2/repository/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar:$HOME/.m2/repository/sslext/sslext/1.2-0/sslext-1.2-0.jar:$HOME/.m2/repository/org/apache/struts/struts-core/1.3.8/struts-core-1.3.8.jar:$HOME/.m2/repository/org/apache/struts/struts-taglib/1.3.8/struts-taglib-1.3.8.jar:$HOME/.m2/repository/org/apache/struts/struts-tiles/1.3.8/struts-tiles-1.3.8.jar:$HOME/.m2/repository/org/apache/velocity/velocity/1.6.2/velocity-1.6.2.jar:$HOME/.m2/repository/org/springframework/spring-web/4.1.6.RELEASE/spring-web-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/spring-webmvc/4.1.6.RELEASE/spring-webmvc-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/spring-expression/4.1.6.RELEASE/spring-expression-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/hibernate/javax/persistence/hibernate-jpa-2.0-api/1.0.0.Final/hibernate-jpa-2.0-api-1.0.0.Final.jar:$HOME/.m2/repository/org/springframework/spring-core/4.1.6.RELEASE/spring-core-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/aspectj/aspectjweaver/1.6.5/aspectjweaver-1.6.5.jar:$HOME/.m2/repository/asm/asm-commons/3.3/asm-commons-3.3.jar:$HOME/.m2/repository/asm/asm-tree/3.3/asm-tree-3.3.jar:$HOME/.m2/repository/asm/asm/3.3/asm-3.3.jar:$HOME/.m2/repository/org/springframework/security/spring-security-core/3.2.6.RELEASE/spring-security-core-3.2.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/spring-beans/4.1.6.RELEASE/spring-beans-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/spring-context/4.1.6.RELEASE/spring-context-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/spring-context-support/4.1.6.RELEASE/spring-context-support-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/spring-orm/4.1.6.RELEASE/spring-orm-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/spring-jdbc/4.1.6.RELEASE/spring-jdbc-4.1.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/security/spring-security-taglibs/3.2.6.RELEASE/spring-security-taglibs-3.2.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/security/spring-security-acl/3.2.6.RELEASE/spring-security-acl-3.2.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/security/spring-security-ldap/3.2.6.RELEASE/spring-security-ldap-3.2.6.RELEASE.jar:$HOME/.m2/repository/org/springframework/ldap/spring-ldap-core/1.3.2.RELEASE/spring-ldap-core-1.3.2.RELEASE.jar:$HOME/.m2/repository/org/quartz-scheduler/quartz/2.2.0/quartz-2.2.0.jar:$HOME/.m2/repository/c3p0/c3p0/0.9.1.1/c3p0-0.9.1.1.jar:$HOME/.m2/repository/oro/oro/2.0.8/oro-2.0.8.jar:$HOME/.m2/repository/net/sf/jsr107cache/jsr107cache/1.0/jsr107cache-1.0.jar:$HOME/.m2/repository/javax/xml/bind/jsr173_api/1.0/jsr173_api-1.0.jar:$HOME/.m2/repository/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar:$HOME/.m2/repository/org/apache/xmlbeans/xmlbeans/2.4.0/xmlbeans-2.4.0.jar:$HOME/.m2/repository/stax/stax-api/1.0.1/stax-api-1.0.1.jar:$HOME/.m2/repository/org/apache/geronimo/specs/geronimo-jms_1.1_spec/1.1.1/geronimo-jms_1.1_spec-1.1.1.jar:$HOME/.m2/repository/eu/medsea/mimeutil/mime-util/2.1.3/mime-util-2.1.3.jar:$HOME/.m2/repository/org/thymeleaf/thymeleaf/2.1.4.RELEASE/thymeleaf-2.1.4.RELEASE.jar:$HOME/.m2/repository/ognl/ognl/3.0.8/ognl-3.0.8.jar:$HOME/.m2/repository/org/unbescape/unbescape/1.1.0.RELEASE/unbescape-1.1.0.RELEASE.jar:$HOME/.m2/repository/org/thymeleaf/thymeleaf-spring4/2.1.4.RELEASE/thymeleaf-spring4-2.1.4.RELEASE.jar:$HOME/.m2/repository/org/owasp/antisamy/antisamy/1.4.5/antisamy-1.4.5.jar:$HOME/.m2/repository/xerces/xercesImpl/2.8.1/xercesImpl-2.8.1.jar:$HOME/.m2/repository/xml-apis/xml-apis/1.3.03/xml-apis-1.3.03.jar:$HOME/.m2/repository/org/apache/xmlgraphics/batik-css/1.7/batik-css-1.7.jar:$HOME/.m2/repository/org/apache/xmlgraphics/batik-ext/1.7/batik-ext-1.7.jar:$HOME/.m2/repository/org/apache/xmlgraphics/batik-util/1.7/batik-util-1.7.jar:$HOME/.m2/repository/xml-apis/xml-apis-ext/1.3.04/xml-apis-ext-1.3.04.jar:$HOME/.m2/repository/net/sourceforge/nekohtml/nekohtml/1.9.12/nekohtml-1.9.12.jar:$HOME/.m2/repository/commons-httpclient/commons-httpclient/3.1/commons-httpclient-3.1.jar:$HOME/.m2/repository/org/apache/commons/commons-lang3/3.3.2/commons-lang3-3.3.2.jar:$HOME/.m2/repository/org/apache/commons/commons-collections4/4.0/commons-collections4-4.0.jar:$HOME/.m2/repository/commons-logging/commons-logging/1.1.3/commons-logging-1.1.3.jar:$HOME/.m2/repository/org/javassist/javassist/3.17.1-GA/javassist-3.17.1-GA.jar:$HOME/.m2/repository/com/fasterxml/jackson/module/jackson-module-jaxb-annotations/2.5.0/jackson-module-jaxb-annotations-2.5.0.jar:$HOME/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.5.0/jackson-core-2.5.0.jar:$HOME/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.5.0/jackson-databind-2.5.0.jar:$HOME/.m2/repository/com/fasterxml/jackson/dataformat/jackson-dataformat-xml/2.5.0/jackson-dataformat-xml-2.5.0.jar:$HOME/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.5.0/jackson-annotations-2.5.0.jar:$HOME/.m2/repository/org/codehaus/woodstox/stax2-api/3.1.4/stax2-api-3.1.4.jar:$HOME/.m2/repository/org/codehaus/woodstox/woodstox-core-asl/4.1.4/woodstox-core-asl-4.1.4.jar:$HOME/.m2/repository/javax/xml/stream/stax-api/1.0-2/stax-api-1.0-2.jar:$HOME/.m2/repository/commons-digester/commons-digester/1.8/commons-digester-1.8.jar:$HOME/.m2/repository/org/hibernate/hibernate-ehcache/4.1.11.Final/hibernate-ehcache-4.1.11.Final.jar:$HOME/.m2/repository/com/yahoo/platform/yui/yuicompressor/2.4.7/yuicompressor-2.4.7.jar:$HOME/.m2/repository/de/jkeylockmanager/jkeylockmanager/1.1.0/jkeylockmanager-1.1.0.jar:$HOME/.m2/repository/org/codehaus/jettison/jettison/1.1/jettison-1.1.jar:$HOME/.m2/repository/org/broadleafcommerce/broadleaf-common/4.0.5-GA/broadleaf-common-4.0.5-GA-tests.jar:$HOME/.m2/repository/org/broadleafcommerce/broadleaf-profile/4.0.5-GA/broadleaf-profile-4.0.5-GA.jar:$HOME/.m2/repository/commons-validator/commons-validator/1.3.1/commons-validator-1.3.1.jar:$HOME/.m2/repository/org/broadleafcommerce/broadleaf-contentmanagement-module/4.0.5-GA/broadleaf-contentmanagement-module-4.0.5-GA.jar:$HOME/.m2/repository/org/broadleafcommerce/broadleaf-open-admin-platform/4.0.5-GA/broadleaf-open-admin-platform-4.0.5-GA.jar:$HOME/.m2/repository/commons-pool/commons-pool/1.5.6/commons-pool-1.5.6.jar:$HOME/.m2/repository/org/hibernate/hibernate-validator-annotation-processor/4.3.0.Final/hibernate-validator-annotation-processor-4.3.0.Final.jar:$HOME/.m2/repository/javax/validation/validation-api/1.0.0.GA/validation-api-1.0.0.GA.jar:$HOME/.m2/repository/com/twelvemonkeys/imageio/imageio-jpeg/3.0-rc5/imageio-jpeg-3.0-rc5.jar:$HOME/.m2/repository/com/twelvemonkeys/imageio/imageio-core/3.0-rc5/imageio-core-3.0-rc5.jar:$HOME/.m2/repository/com/twelvemonkeys/imageio/imageio-metadata/3.0-rc5/imageio-metadata-3.0-rc5.jar:$HOME/.m2/repository/com/twelvemonkeys/common/common-io/3.0-rc5/common-io-3.0-rc5.jar:$HOME/.m2/repository/com/twelvemonkeys/common/common-lang/3.0-rc5/common-lang-3.0-rc5.jar:$HOME/.m2/repository/com/twelvemonkeys/common/common-image/3.0-rc5/common-image-3.0-rc5.jar:$HOME/.m2/repository/commons-codec/commons-codec/1.4/commons-codec-1.4.jar:$HOME/.m2/repository/junit/junit/4.11/junit-4.11.jar:$HOME/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:$HOME/.m2/repository/org/easymock/easymock/2.5.1/easymock-2.5.1.jar:$HOME/.m2/repository/org/easymock/easymockclassextension/2.4/easymockclassextension-2.4.jar:$HOME/.m2/repository/cglib/cglib-nodep/2.1_3/cglib-nodep-2.1_3.jar:$HOME/.m2/repository/joda-time/joda-time/2.1/joda-time-2.1.jar:$HOME/.m2/repository/net/sf/ehcache/ehcache/2.7.2/ehcache-2.7.2.jar:$HOME/.m2/repository/org/codehaus/groovy/groovy-all/2.3.10/groovy-all-2.3.10.jar:$HOME/.m2/repository/org/spockframework/spock-core/1.0-groovy-2.3/spock-core-1.0-groovy-2.3.jar:$HOME/.m2/repository/javax/servlet/servlet-api/2.5/servlet-api-2.5.jar

jPerturb=jPerturb/target/jPerturb-0.0.1-SNAPSHOT-jar-with-dependencies.jar

#admin_modules=(./admin/broadleaf-admin-module/src/main/java/)
#admin_modules=(./admin/broadleaf-contentmanagement-module/src/main/java/ ./admin/broadleaf-open-admin-platform/src/main/java/)
#common=./common/src/main/java/
core_modules=(BroadleafCommerce/core/broadleaf-framework/src/main/java/)
#./core/broadleaf-framework-web/src/main/java/)
#./core/broadleaf-profile/src/main/java/ ./core/broadleaf-profile-web/src/main/java/)

for i in "${core_modules[@]}"
do
    echo "java -cp $jPerturb main.Main -type IntNum:boolean -i $i -x"
    java -cp $dep_core_framework:$jPerturb main.Main -type IntNum:boolean -i $i -x 
done
