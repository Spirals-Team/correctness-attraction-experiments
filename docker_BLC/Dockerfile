# Dockerfile
FROM java:8

RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates wget git maven ant net-tools

COPY perturb.sh /

RUN git clone https://github.com/BroadleafCommerce/BroadleafCommerce.git && \
	cd BroadleafCommerce && \
	git checkout broadleaf-4.0.5-GA 

RUN cd BroadleafCommerce && \
	mvn verify clean -Pblc-development --fail-never

RUN git clone https://github.com/Spirals-Team/jPerturb.git

RUN cd jPerturb && mvn install package

RUN cd BroadleafCommerce && \
	mvn install --fail-never -Pblc-development

RUN git clone https://github.com/BroadleafCommerce/DemoSite.git && \
	cd DemoSite && \
	git checkout 44bdb30

RUN cd DemoSite && \
	mvn verify clean --fail-never

RUN chmod 755 perturb.sh && \
	bash perturb.sh

RUN cd BroadleafCommerce/core && \
	git checkout broadleaf-framework/src/main/java/org/broadleafcommerce/core/catalog/domain/

RUN cd BroadleafCommerce/core && \
	git checkout broadleaf-framework/src/main/java/org/broadleafcommerce/core/search/service/solr/

RUN cd BroadleafCommerce/core && \
	git checkout broadleaf-framework/src/main/java/org/broadleafcommerce/core/order/domain/

RUN cd BroadleafCommerce/core && \
	git checkout broadleaf-framework/src/main/java/org/broadleafcommerce/core/catalog/service/RelatedProductsServiceImpl.java

RUN cd BroadleafCommerce/core && \
	git checkout broadleaf-framework/src/main/java/org/broadleafcommerce/core/checkout/service/workflow/DecrementInventoryActivity.java

RUN cd BroadleafCommerce/core && \
	git checkout broadleaf-framework/src/main/java/org/broadleafcommerce/core/order/service/

RUN cd BroadleafCommerce/core && \
	git checkout broadleaf-framework/src/main/java/org/broadleafcommerce/core/order/strategy/


COPY StartStopListener.java /DemoSite/site/src/main/java/com/mycompany/controller/StartStopListener.java

COPY pom_parent.xml BroadleafCommerce/pom.xml

COPY pom_core_framework.xml BroadleafCommerce/core/broadleaf-framework/pom.xml

COPY pom_demosite.xml Demosite/pom.xml

RUN cd BroadleafCommerce && \
	mvn install -Pblc-development

RUN cd DemoSite && \
	mvn clean install

COPY entrypoint.sh /
ENTRYPOINT ["/entrypoint.sh"]

EXPOSE 8000
EXPOSE 8080
EXPOSE 8081
EXPOSE 13223

