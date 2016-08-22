package com.mycompany.controller;

import perturbation.rmi.PerturbationServer;
import perturbation.rmi.PerturbationServerImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@WebListener
public class StartStopListener implements ServletContextListener {

	private static final String PATH = "/BroadleafCommerce/core/broadleaf-framework/src/main/java/org/broadleafcommerce/core/";

	private static final String ROOT_PACKAGE = "org.broadleafcommerce.core";

	private static PerturbationServer server;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		if (server == null) {
			try {
				PerturbationServerImpl.startServer(PATH, ROOT_PACKAGE);
				Thread.sleep(500);
				Registry registry = LocateRegistry.getRegistry(PerturbationServerImpl.PORT);
				this.server = (PerturbationServer) registry.lookup(PerturbationServerImpl.NAME_SERVER);
				System.out.println("Number of Locations in core:BLC-framework : " + server.getLocations().size());
				System.out.println("Servlet has been started.");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Error when creating RMI object");
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		try {
			this.server.stopService();
		} catch (Exception ignored) {}
		System.out.println("Servlet has been stopped.");
	}
}
