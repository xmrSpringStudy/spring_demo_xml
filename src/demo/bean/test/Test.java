package demo.bean.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.parsing.EmptyReaderEventListener;
import org.springframework.beans.factory.parsing.FailFastProblemReporter;
import org.springframework.beans.factory.parsing.NullSourceExtractor;
import org.springframework.beans.factory.parsing.ProblemReporter;
import org.springframework.beans.factory.parsing.ReaderEventListener;
import org.springframework.beans.factory.parsing.SourceExtractor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.beans.factory.xml.DefaultDocumentLoader;
import org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver;
import org.springframework.beans.factory.xml.DocumentLoader;
import org.springframework.beans.factory.xml.NamespaceHandlerResolver;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlReaderContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.xml.SimpleSaxErrorHandler;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;

import demo.bean.Person;
import org.xml.sax.EntityResolver;

public class Test {
	 public static void main(String[] args) {

		 System.out.println("-------------test BeanFactory by ClassPathXmlApplicationContext-----------------");
		 testApplicationContext();
		 System.out.println("-------------test  BeanFactory  by ClassPathResource-----------------");
		 testBeanFactory(); 
		 System.out.println("-------------test detail of BeanFactory  by ClassPathResource-----------------");
		 testBeanFactoryDetail();
		 System.out.println("-------------test BeanFactory  by File Application Context-----------------");
		 testFileApplicationContext();
	 }
	 
	 private static void testApplicationContext() {
		 // from https://www.cnblogs.com/linjiaxin/p/5866970.html
		 ApplicationContext ctx = new ClassPathXmlApplicationContext("bean.xml");//读取bean.xml中的内容
		 callBean(ctx);
	 }
	 
	 private static void testBeanFactoryDetail() {
		 try {
				onTest(); 
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
			catch(Exception except) {
				except.printStackTrace();
			}
			finally {
			}
	 }	 
	 
	 private static void testBeanFactory() {
		 // from https://www.cnblogs.com/ITtangtang/p/3978349.html#a2
		//根据Xml配置文件创建Resource资源对象，该对象中包含了BeanDefinition的信息
		 ClassPathResource resource =new ClassPathResource("bean.xml");
		//创建DefaultListableBeanFactory
		 DefaultListableBeanFactory factory =new DefaultListableBeanFactory();
		//创建XmlBeanDefinitionReader读取器，用于载入BeanDefinition。之所以需要BeanFactory作为参数，是因为会将读取的信息回调配置给factory
		 XmlBeanDefinitionReader reader =new XmlBeanDefinitionReader(factory);
		//XmlBeanDefinitionReader执行载入BeanDefinition的方法，最后会完成Bean的载入和注册。完成后Bean就成功的放置到IOC容器当中，以后我们就可以从中取得Bean来使用
		 reader.loadBeanDefinitions(resource);
		 callBean(factory);
	 }
	 
	private static void testFileApplicationContext() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("bin/bean.xml");
		 callBean(ctx);
	}
	 private static void onTest() throws IOException {
		//根据Xml配置文件创建Resource资源对象，该对象中包含了BeanDefinition的信息
		 ClassPathResource resource =new ClassPathResource("bean.xml");
		 InputStream inputStream = resource.getInputStream();
			try {
				InputSource inputSource = new InputSource(inputStream);
				
				 DefaultResourceLoader resourceLoad = new DefaultResourceLoader();
				 
				 Document doc = createDocument(inputSource, resourceLoad);
				 
				 DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
				 
				 XmlReaderContext readerContext = createReaderContext(resourceLoad, resource, factory);
				 
				 BeanDefinitionDocumentReader documentReader = new DefaultBeanDefinitionDocumentReader();
				 
				 documentReader.registerBeanDefinitions(doc, readerContext);
				 
				 //reader.registerBeanDefinitions(doc, resource);
				 callBean(factory);
			}
			finally {
				inputStream.close();
			}
		 
	 }
	 
	 private static Document createDocument(InputSource inputSource, DefaultResourceLoader resourceLoad) {
		 DocumentLoader documentLoader = new DefaultDocumentLoader();
		 EntityResolver entityResolver = new ResourceEntityResolver(resourceLoad);
		 
		 Log logger = LogFactory.getLog(Test.class);
		 ErrorHandler errorHandler = new SimpleSaxErrorHandler(logger);
		try {
			return documentLoader.loadDocument(
						inputSource, entityResolver, errorHandler, 3, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	 }
	 
	 private static XmlReaderContext createReaderContext(DefaultResourceLoader resourceLoad, ClassPathResource resource, DefaultListableBeanFactory factory) {
		 
		 NamespaceHandlerResolver namespaceHandlerResolver = new DefaultNamespaceHandlerResolver(resourceLoad.getClassLoader());
		 
		 // 下面这三个暂时忽略吧
		 ProblemReporter problemReporter = new FailFastProblemReporter();
		 ReaderEventListener eventListener = new EmptyReaderEventListener();
		 SourceExtractor sourceExtractor = new NullSourceExtractor();
		 
		//创建XmlBeanDefinitionReader读取器，用于载入BeanDefinition。之所以需要BeanFactory作为参数，是因为会将读取的信息回调配置给factory
		 XmlBeanDefinitionReader reader =new XmlBeanDefinitionReader(factory);
		 
		 return new XmlReaderContext(resource, problemReporter, eventListener,
					sourceExtractor, reader, namespaceHandlerResolver);
	 }
	 
	 private static void callBean(BeanFactory factory) {
		 System.out.println("prepare call now!");
		 Person p = factory.getBean("person", Person.class);
		 p.info();
		 
		 p = factory.getBean("a", Person.class);
		 p.info();
		 
		 p = factory.getBean("d", Person.class);
		 p.info();
		 p.booksInfo();
		 
		 // call person2 bean
		 System.out.println("getBean!");
		 p = factory.getBean("person2", Person.class);
		 p.info();
		 
		 if (!DefaultSingletonBeanRegistry.class.isInstance(factory)) {
			 return;
		 }
		 
		 DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry)factory;
		 
		 System.out.println("destory alias now!");
		 registry.destroySingleton("d");
		 System.out.println("destory bean now!");
		 registry.destroySingleton("person");
		 registry.destroySingleton("person2");
		 //factory.destroySingletons();
	 }
}
