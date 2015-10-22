/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloWorld;

import org.mt4j.MTApplication;

/**
 *
 * @author jolak
 */
public class HelloWorld extends MTApplication {
		private static final long serialVersionUID = 1L;
		
		public static void main(String[] args) {
			initialize();
		}
		@Override
		public void startUp() {
			addScene(new HelloWorldExample(this, "Hello World Scene"));
		}
}
