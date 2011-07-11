/*
* generated by Xtext
*/
package org.osate.xtext.aadl2.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.DataImplementation;
import org.osate.aadl2.DataPort;
import org.osate.aadl2.DataSubcomponent;
import org.osate.aadl2.DataType;
import org.osate.aadl2.EventDataPort;
import org.osate.aadl2.EventPort;
import org.osate.aadl2.PrivatePackageSection;
import org.osate.aadl2.ProcessorType;
import org.osate.aadl2.PublicPackageSection;
import org.osate.aadl2.SystemImplementation;
import org.osate.aadl2.SystemSubcomponent;
import org.osate.aadl2.SystemType;

import com.google.inject.Inject;

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class Aadl2LabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public Aadl2LabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}


	//Labels and icons can be computed like this:
	
	String text(AadlPackage ele) {
		  return "Package "+ele.getName();
		}
	String text(PublicPackageSection ele) {
		  return "Public";
		}
	String text(PrivatePackageSection ele) {
		  return "Private";
		}
	String text(SystemType ele) {
		  return "System "+ele.getName();
		}
	String text(ProcessorType ele) {
		  return "Processor "+ele.getName();
		}
	String text(DataType ele) {
		  return "Data "+ele.getName();
		}
	String text(SystemImplementation ele) {
		  return "System Impl "+ele.getName();
		}
	String text(DataImplementation ele) {
		  return "Data Impl "+ele.getName();
		}
	String text(SystemSubcomponent ele) {
		  return "System Subcomponent "+ele.getName();
		}
	String text(DataSubcomponent ele) {
		  return "Data Subcomponent "+ele.getName();
		}
	String text(EventPort ele) {
		  return "Event Port "+ele.getName();
		}
	String text(DataPort ele) {
		  return "Data Port "+ele.getName();
		}
	String text(EventDataPort ele) {
		  return "Event Data Port "+ele.getName();
		}
	 

}