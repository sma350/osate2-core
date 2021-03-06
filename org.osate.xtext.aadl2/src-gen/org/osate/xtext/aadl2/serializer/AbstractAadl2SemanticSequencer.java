package org.osate.xtext.aadl2.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.osate.aadl2.Aadl2Package;
import org.osate.aadl2.AadlBoolean;
import org.osate.aadl2.AadlInteger;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.AadlReal;
import org.osate.aadl2.AadlString;
import org.osate.aadl2.AbstractFeature;
import org.osate.aadl2.AbstractImplementation;
import org.osate.aadl2.AbstractPrototype;
import org.osate.aadl2.AbstractSubcomponent;
import org.osate.aadl2.AbstractType;
import org.osate.aadl2.AccessConnection;
import org.osate.aadl2.AccessSpecification;
import org.osate.aadl2.ArrayDimension;
import org.osate.aadl2.ArrayRange;
import org.osate.aadl2.ArraySize;
import org.osate.aadl2.BasicProperty;
import org.osate.aadl2.BasicPropertyAssociation;
import org.osate.aadl2.BooleanLiteral;
import org.osate.aadl2.BusAccess;
import org.osate.aadl2.BusImplementation;
import org.osate.aadl2.BusPrototype;
import org.osate.aadl2.BusSubcomponent;
import org.osate.aadl2.BusType;
import org.osate.aadl2.ClassifierType;
import org.osate.aadl2.ClassifierValue;
import org.osate.aadl2.ComponentImplementationReference;
import org.osate.aadl2.ComponentPrototypeActual;
import org.osate.aadl2.ComponentPrototypeBinding;
import org.osate.aadl2.ComponentTypeRename;
import org.osate.aadl2.ComputedValue;
import org.osate.aadl2.ConnectedElement;
import org.osate.aadl2.ContainedNamedElement;
import org.osate.aadl2.ContainmentPathElement;
import org.osate.aadl2.DataAccess;
import org.osate.aadl2.DataImplementation;
import org.osate.aadl2.DataPort;
import org.osate.aadl2.DataPrototype;
import org.osate.aadl2.DataSubcomponent;
import org.osate.aadl2.DataType;
import org.osate.aadl2.DefaultAnnexLibrary;
import org.osate.aadl2.DefaultAnnexSubclause;
import org.osate.aadl2.DeviceImplementation;
import org.osate.aadl2.DevicePrototype;
import org.osate.aadl2.DeviceSubcomponent;
import org.osate.aadl2.DeviceType;
import org.osate.aadl2.EndToEndFlow;
import org.osate.aadl2.EndToEndFlowSegment;
import org.osate.aadl2.EnumerationLiteral;
import org.osate.aadl2.EnumerationType;
import org.osate.aadl2.EventDataPort;
import org.osate.aadl2.EventDataSource;
import org.osate.aadl2.EventPort;
import org.osate.aadl2.EventSource;
import org.osate.aadl2.FeatureConnection;
import org.osate.aadl2.FeatureGroup;
import org.osate.aadl2.FeatureGroupConnection;
import org.osate.aadl2.FeatureGroupPrototype;
import org.osate.aadl2.FeatureGroupPrototypeActual;
import org.osate.aadl2.FeatureGroupPrototypeBinding;
import org.osate.aadl2.FeatureGroupType;
import org.osate.aadl2.FeatureGroupTypeRename;
import org.osate.aadl2.FeaturePrototype;
import org.osate.aadl2.FeaturePrototypeBinding;
import org.osate.aadl2.FeaturePrototypeReference;
import org.osate.aadl2.FlowEnd;
import org.osate.aadl2.FlowImplementation;
import org.osate.aadl2.FlowSegment;
import org.osate.aadl2.FlowSpecification;
import org.osate.aadl2.GroupExtension;
import org.osate.aadl2.ImplementationExtension;
import org.osate.aadl2.IntegerLiteral;
import org.osate.aadl2.ListType;
import org.osate.aadl2.ListValue;
import org.osate.aadl2.MemoryImplementation;
import org.osate.aadl2.MemoryPrototype;
import org.osate.aadl2.MemorySubcomponent;
import org.osate.aadl2.MemoryType;
import org.osate.aadl2.MetaclassReference;
import org.osate.aadl2.ModalPropertyValue;
import org.osate.aadl2.Mode;
import org.osate.aadl2.ModeBinding;
import org.osate.aadl2.ModeTransition;
import org.osate.aadl2.ModeTransitionTrigger;
import org.osate.aadl2.NamedValue;
import org.osate.aadl2.NumericRange;
import org.osate.aadl2.Operation;
import org.osate.aadl2.PackageRename;
import org.osate.aadl2.Parameter;
import org.osate.aadl2.ParameterConnection;
import org.osate.aadl2.PortConnection;
import org.osate.aadl2.PortProxy;
import org.osate.aadl2.PortSpecification;
import org.osate.aadl2.PrivatePackageSection;
import org.osate.aadl2.ProcessImplementation;
import org.osate.aadl2.ProcessPrototype;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.ProcessType;
import org.osate.aadl2.ProcessorImplementation;
import org.osate.aadl2.ProcessorPrototype;
import org.osate.aadl2.ProcessorSubcomponent;
import org.osate.aadl2.ProcessorType;
import org.osate.aadl2.Property;
import org.osate.aadl2.PropertyAssociation;
import org.osate.aadl2.PropertyConstant;
import org.osate.aadl2.PropertySet;
import org.osate.aadl2.PublicPackageSection;
import org.osate.aadl2.RangeType;
import org.osate.aadl2.RangeValue;
import org.osate.aadl2.RealLiteral;
import org.osate.aadl2.Realization;
import org.osate.aadl2.RecordType;
import org.osate.aadl2.RecordValue;
import org.osate.aadl2.ReferenceType;
import org.osate.aadl2.ReferenceValue;
import org.osate.aadl2.StringLiteral;
import org.osate.aadl2.SubprogramAccess;
import org.osate.aadl2.SubprogramCall;
import org.osate.aadl2.SubprogramCallSequence;
import org.osate.aadl2.SubprogramGroupAccess;
import org.osate.aadl2.SubprogramGroupImplementation;
import org.osate.aadl2.SubprogramGroupPrototype;
import org.osate.aadl2.SubprogramGroupSubcomponent;
import org.osate.aadl2.SubprogramGroupType;
import org.osate.aadl2.SubprogramImplementation;
import org.osate.aadl2.SubprogramPrototype;
import org.osate.aadl2.SubprogramProxy;
import org.osate.aadl2.SubprogramSubcomponent;
import org.osate.aadl2.SubprogramType;
import org.osate.aadl2.SystemImplementation;
import org.osate.aadl2.SystemPrototype;
import org.osate.aadl2.SystemSubcomponent;
import org.osate.aadl2.SystemType;
import org.osate.aadl2.ThreadGroupImplementation;
import org.osate.aadl2.ThreadGroupPrototype;
import org.osate.aadl2.ThreadGroupSubcomponent;
import org.osate.aadl2.ThreadGroupType;
import org.osate.aadl2.ThreadImplementation;
import org.osate.aadl2.ThreadPrototype;
import org.osate.aadl2.ThreadSubcomponent;
import org.osate.aadl2.ThreadType;
import org.osate.aadl2.TypeExtension;
import org.osate.aadl2.UnitLiteral;
import org.osate.aadl2.UnitsType;
import org.osate.aadl2.VirtualBusImplementation;
import org.osate.aadl2.VirtualBusPrototype;
import org.osate.aadl2.VirtualBusSubcomponent;
import org.osate.aadl2.VirtualBusType;
import org.osate.aadl2.VirtualProcessorImplementation;
import org.osate.aadl2.VirtualProcessorPrototype;
import org.osate.aadl2.VirtualProcessorSubcomponent;
import org.osate.aadl2.VirtualProcessorType;
import org.osate.xtext.aadl2.properties.serializer.PropertiesSemanticSequencer;
import org.osate.xtext.aadl2.services.Aadl2GrammarAccess;

@SuppressWarnings("all")
public abstract class AbstractAadl2SemanticSequencer extends PropertiesSemanticSequencer {

	@Inject
	private Aadl2GrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == Aadl2Package.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case Aadl2Package.AADL_BOOLEAN:
				if(context == grammarAccess.getBooleanTypeRule() ||
				   context == grammarAccess.getPropertyTypeRule()) {
					sequence_BooleanType(context, (AadlBoolean) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnnamedBooleanTypeRule() ||
				   context == grammarAccess.getUnnamedPropertyTypeRule()) {
					sequence_UnnamedBooleanType(context, (AadlBoolean) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.AADL_INTEGER:
				if(context == grammarAccess.getIntegerTypeRule() ||
				   context == grammarAccess.getPropertyTypeRule()) {
					sequence_IntegerType(context, (AadlInteger) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnnamedIntegerTypeRule() ||
				   context == grammarAccess.getUnnamedPropertyTypeRule()) {
					sequence_UnnamedIntegerType(context, (AadlInteger) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.AADL_PACKAGE:
				if(context == grammarAccess.getAadlPackageRule() ||
				   context == grammarAccess.getModelRule()) {
					sequence_AadlPackage(context, (AadlPackage) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.AADL_REAL:
				if(context == grammarAccess.getPropertyTypeRule() ||
				   context == grammarAccess.getRealTypeRule()) {
					sequence_RealType(context, (AadlReal) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnnamedPropertyTypeRule() ||
				   context == grammarAccess.getUnnamedRealTypeRule()) {
					sequence_UnnamedRealType(context, (AadlReal) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.AADL_STRING:
				if(context == grammarAccess.getPropertyTypeRule() ||
				   context == grammarAccess.getStringTypeRule()) {
					sequence_StringType(context, (AadlString) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnnamedPropertyTypeRule() ||
				   context == grammarAccess.getUnnamedStringTypeRule()) {
					sequence_UnnamedStringType(context, (AadlString) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ABSTRACT_FEATURE:
				if(context == grammarAccess.getAbstractFeatureRule()) {
					sequence_AbstractFeature(context, (AbstractFeature) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ABSTRACT_IMPLEMENTATION:
				if(context == grammarAccess.getAbstractImplementationRule() ||
				   context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule()) {
					sequence_AbstractImplementation(context, (AbstractImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ABSTRACT_PROTOTYPE:
				if(context == grammarAccess.getAbstractPrototypeRule() ||
				   context == grammarAccess.getComponentPrototypeRule()) {
					sequence_AbstractPrototype(context, (AbstractPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPrototypeRule()) {
					sequence_AbstractPrototype_Prototype(context, (AbstractPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ABSTRACT_SUBCOMPONENT:
				if(context == grammarAccess.getAbstractSubcomponentRule()) {
					sequence_AbstractSubcomponent(context, (AbstractSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ABSTRACT_TYPE:
				if(context == grammarAccess.getAbstractTypeRule() ||
				   context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule()) {
					sequence_AbstractType(context, (AbstractType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ACCESS_CONNECTION:
				if(context == grammarAccess.getAccessConnectionRule()) {
					sequence_AccessConnection(context, (AccessConnection) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ACCESS_SPECIFICATION:
				if(context == grammarAccess.getAccessSpecificationRule()) {
					sequence_AccessSpecification(context, (AccessSpecification) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ARRAY_DIMENSION:
				if(context == grammarAccess.getArrayDimensionRule()) {
					sequence_ArrayDimension(context, (ArrayDimension) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ARRAY_RANGE:
				if(context == grammarAccess.getArrayRangeRule()) {
					sequence_ArrayRange(context, (ArrayRange) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ARRAY_SIZE:
				if(context == grammarAccess.getArraySizeRule()) {
					sequence_ArraySize(context, (ArraySize) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.BASIC_PROPERTY:
				if(context == grammarAccess.getRecordFieldRule()) {
					sequence_RecordField(context, (BasicProperty) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.BASIC_PROPERTY_ASSOCIATION:
				if(context == grammarAccess.getFieldPropertyAssociationRule()) {
					sequence_FieldPropertyAssociation(context, (BasicPropertyAssociation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.BOOLEAN_LITERAL:
				if(context == grammarAccess.getBooleanLiteralRule() ||
				   context == grammarAccess.getConstantPropertyExpressionRule() ||
				   context == grammarAccess.getPropertyExpressionRule()) {
					sequence_BooleanLiteral(context, (BooleanLiteral) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.BUS_ACCESS:
				if(context == grammarAccess.getBusAccessRule()) {
					sequence_BusAccess(context, (BusAccess) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.BUS_IMPLEMENTATION:
				if(context == grammarAccess.getBusImplementationRule() ||
				   context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule()) {
					sequence_BusImplementation(context, (BusImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.BUS_PROTOTYPE:
				if(context == grammarAccess.getBusPrototypeRule() ||
				   context == grammarAccess.getComponentPrototypeRule()) {
					sequence_BusPrototype(context, (BusPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPrototypeRule()) {
					sequence_BusPrototype_Prototype(context, (BusPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.BUS_SUBCOMPONENT:
				if(context == grammarAccess.getBusSubcomponentRule()) {
					sequence_BusSubcomponent(context, (BusSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.BUS_TYPE:
				if(context == grammarAccess.getBusTypeRule() ||
				   context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule()) {
					sequence_BusType(context, (BusType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.CLASSIFIER_TYPE:
				if(context == grammarAccess.getClassifierTypeRule() ||
				   context == grammarAccess.getPropertyTypeRule()) {
					sequence_ClassifierType(context, (ClassifierType) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnnamedClassifierTypeRule() ||
				   context == grammarAccess.getUnnamedPropertyTypeRule()) {
					sequence_UnnamedClassifierType(context, (ClassifierType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.CLASSIFIER_VALUE:
				if(context == grammarAccess.getComponentClassifierTermRule() ||
				   context == grammarAccess.getConstantPropertyExpressionRule() ||
				   context == grammarAccess.getPropertyExpressionRule()) {
					sequence_ComponentClassifierTerm(context, (ClassifierValue) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPropertyOwnerRule() ||
				   context == grammarAccess.getQCReferenceRule()) {
					sequence_QCReference(context, (ClassifierValue) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.COMPONENT_IMPLEMENTATION_REFERENCE:
				if(context == grammarAccess.getComponentImplementationReferenceRule()) {
					sequence_ComponentImplementationReference(context, (ComponentImplementationReference) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.COMPONENT_PROTOTYPE_ACTUAL:
				if(context == grammarAccess.getComponentReferenceRule()) {
					sequence_ComponentReference(context, (ComponentPrototypeActual) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.COMPONENT_PROTOTYPE_BINDING:
				if(context == grammarAccess.getComponentPrototypeBindingRule() ||
				   context == grammarAccess.getPrototypeBindingRule()) {
					sequence_ComponentPrototypeBinding(context, (ComponentPrototypeBinding) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.COMPONENT_TYPE_RENAME:
				if(context == grammarAccess.getCTRenameRule()) {
					sequence_CTRename(context, (ComponentTypeRename) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.COMPUTED_VALUE:
				if(context == grammarAccess.getComputedTermRule() ||
				   context == grammarAccess.getConstantPropertyExpressionRule() ||
				   context == grammarAccess.getPropertyExpressionRule()) {
					sequence_ComputedTerm(context, (ComputedValue) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.CONNECTED_ELEMENT:
				if(context == grammarAccess.getAbstractConnectionEndRule()) {
					sequence_AbstractConnectionEnd_ConnectedElement_InternalEvent_ProcessorPort(context, (ConnectedElement) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getAccessConnectionEndRule()) {
					sequence_AccessConnectionEnd_ConnectedElement_ProcessorSubprogram(context, (ConnectedElement) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getConnectedElementRule()) {
					sequence_ConnectedElement(context, (ConnectedElement) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getProcessorConnectionEndRule()) {
					sequence_ConnectedElement_ProcessorConnectionEnd_ProcessorPort(context, (ConnectedElement) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getInternalEventRule()) {
					sequence_InternalEvent(context, (ConnectedElement) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getProcessorPortRule()) {
					sequence_ProcessorPort(context, (ConnectedElement) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getProcessorSubprogramRule()) {
					sequence_ProcessorSubprogram(context, (ConnectedElement) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.CONTAINED_NAMED_ELEMENT:
				if(context == grammarAccess.getContainmentPathRule()) {
					sequence_ContainmentPath(context, (ContainedNamedElement) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.CONTAINMENT_PATH_ELEMENT:
				if(context == grammarAccess.getContainmentPathElementRule()) {
					sequence_ContainmentPathElement(context, (ContainmentPathElement) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DATA_ACCESS:
				if(context == grammarAccess.getDataAccessRule()) {
					sequence_DataAccess(context, (DataAccess) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DATA_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getDataImplementationRule()) {
					sequence_DataImplementation(context, (DataImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DATA_PORT:
				if(context == grammarAccess.getDataPortRule()) {
					sequence_DataPort(context, (DataPort) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DATA_PROTOTYPE:
				if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getDataPrototypeRule()) {
					sequence_DataPrototype(context, (DataPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPrototypeRule()) {
					sequence_DataPrototype_Prototype(context, (DataPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DATA_SUBCOMPONENT:
				if(context == grammarAccess.getDataSubcomponentRule()) {
					sequence_DataSubcomponent(context, (DataSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DATA_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getDataTypeRule()) {
					sequence_DataType(context, (DataType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DEFAULT_ANNEX_LIBRARY:
				if(context == grammarAccess.getAnnexLibraryRule() ||
				   context == grammarAccess.getDefaultAnnexLibraryRule()) {
					sequence_DefaultAnnexLibrary(context, (DefaultAnnexLibrary) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DEFAULT_ANNEX_SUBCLAUSE:
				if(context == grammarAccess.getAnnexSubclauseRule() ||
				   context == grammarAccess.getDefaultAnnexSubclauseRule()) {
					sequence_DefaultAnnexSubclause(context, (DefaultAnnexSubclause) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DEVICE_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getDeviceImplementationRule()) {
					sequence_DeviceImplementation(context, (DeviceImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DEVICE_PROTOTYPE:
				if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getDevicePrototypeRule()) {
					sequence_DevicePrototype(context, (DevicePrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPrototypeRule()) {
					sequence_DevicePrototype_Prototype(context, (DevicePrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DEVICE_SUBCOMPONENT:
				if(context == grammarAccess.getDeviceSubcomponentRule()) {
					sequence_DeviceSubcomponent(context, (DeviceSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.DEVICE_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getDeviceTypeRule()) {
					sequence_DeviceType(context, (DeviceType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.END_TO_END_FLOW:
				if(context == grammarAccess.getEndToEndFlowRule()) {
					sequence_EndToEndFlow(context, (EndToEndFlow) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.END_TO_END_FLOW_SEGMENT:
				if(context == grammarAccess.getETEConnectionFlowRule()) {
					sequence_ETEConnectionFlow(context, (EndToEndFlowSegment) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getETESubcomponentFlowRule()) {
					sequence_ETESubcomponentFlow(context, (EndToEndFlowSegment) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ENUMERATION_LITERAL:
				if(context == grammarAccess.getEnumerationLiteralRule()) {
					sequence_EnumerationLiteral(context, (EnumerationLiteral) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.ENUMERATION_TYPE:
				if(context == grammarAccess.getEnumerationTypeRule() ||
				   context == grammarAccess.getPropertyTypeRule()) {
					sequence_EnumerationType(context, (EnumerationType) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnnamedEnumerationTypeRule() ||
				   context == grammarAccess.getUnnamedPropertyTypeRule()) {
					sequence_UnnamedEnumerationType(context, (EnumerationType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.EVENT_DATA_PORT:
				if(context == grammarAccess.getEventDataPortRule()) {
					sequence_EventDataPort(context, (EventDataPort) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.EVENT_DATA_SOURCE:
				if(context == grammarAccess.getEventDataSourceRule() ||
				   context == grammarAccess.getInternalFeatureRule()) {
					sequence_EventDataSource(context, (EventDataSource) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.EVENT_PORT:
				if(context == grammarAccess.getEventPortRule()) {
					sequence_EventPort(context, (EventPort) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.EVENT_SOURCE:
				if(context == grammarAccess.getEventSourceRule() ||
				   context == grammarAccess.getInternalFeatureRule()) {
					sequence_EventSource(context, (EventSource) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_CONNECTION:
				if(context == grammarAccess.getFeatureConnectionRule()) {
					sequence_FeatureConnection(context, (FeatureConnection) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_GROUP:
				if(context == grammarAccess.getFeatureGroupRule()) {
					sequence_FeatureGroup(context, (FeatureGroup) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_GROUP_CONNECTION:
				if(context == grammarAccess.getFeatureGroupConnectionRule()) {
					sequence_FeatureGroupConnection(context, (FeatureGroupConnection) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_GROUP_PROTOTYPE:
				if(context == grammarAccess.getFeatureGroupPrototypeRule()) {
					sequence_FeatureGroupPrototype(context, (FeatureGroupPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPrototypeRule()) {
					sequence_FeatureGroupPrototype_Prototype(context, (FeatureGroupPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_GROUP_PROTOTYPE_ACTUAL:
				if(context == grammarAccess.getFeatureGroupPrototypeActualRule()) {
					sequence_FeatureGroupPrototypeActual(context, (FeatureGroupPrototypeActual) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_GROUP_PROTOTYPE_BINDING:
				if(context == grammarAccess.getFeatureGroupPrototypeBindingRule() ||
				   context == grammarAccess.getPrototypeBindingRule()) {
					sequence_FeatureGroupPrototypeBinding(context, (FeatureGroupPrototypeBinding) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_GROUP_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getFeatureGroupTypeRule()) {
					sequence_FeatureGroupType(context, (FeatureGroupType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_GROUP_TYPE_RENAME:
				if(context == grammarAccess.getFGTRenameRule()) {
					sequence_FGTRename(context, (FeatureGroupTypeRename) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_PROTOTYPE:
				if(context == grammarAccess.getFeaturePrototypeRule()) {
					sequence_FeaturePrototype(context, (FeaturePrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPrototypeRule()) {
					sequence_FeaturePrototype_Prototype(context, (FeaturePrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_PROTOTYPE_BINDING:
				if(context == grammarAccess.getFeaturePrototypeBindingRule() ||
				   context == grammarAccess.getPrototypeBindingRule()) {
					sequence_FeaturePrototypeBinding(context, (FeaturePrototypeBinding) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FEATURE_PROTOTYPE_REFERENCE:
				if(context == grammarAccess.getFeaturePrototypeReferenceRule()) {
					sequence_FeaturePrototypeReference(context, (FeaturePrototypeReference) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FLOW_END:
				if(context == grammarAccess.getFlowEndRule()) {
					sequence_FlowEnd(context, (FlowEnd) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FLOW_IMPLEMENTATION:
				if(context == grammarAccess.getFlowImplementationRule()) {
					sequence_FlowImplementation_FlowPathImpl_FlowSinkImpl_FlowSourceImpl(context, (FlowImplementation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getFlowPathImplRule()) {
					sequence_FlowPathImpl(context, (FlowImplementation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getFlowSinkImplRule()) {
					sequence_FlowSinkImpl(context, (FlowImplementation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getFlowSourceImplRule()) {
					sequence_FlowSourceImpl(context, (FlowImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FLOW_SEGMENT:
				if(context == grammarAccess.getConnectionFlowRule()) {
					sequence_ConnectionFlow(context, (FlowSegment) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getSubcomponentFlowRule()) {
					sequence_SubcomponentFlow(context, (FlowSegment) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.FLOW_SPECIFICATION:
				if(context == grammarAccess.getFlowSpecificationRule()) {
					sequence_FlowPathSpec_FlowSinkSpec_FlowSourceSpec_FlowSpecRefinement_FlowSpecification(context, (FlowSpecification) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getFlowPathSpecRule()) {
					sequence_FlowPathSpec(context, (FlowSpecification) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getFlowSinkSpecRule()) {
					sequence_FlowSinkSpec(context, (FlowSpecification) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getFlowSourceSpecRule()) {
					sequence_FlowSourceSpec(context, (FlowSpecification) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getFlowSpecRefinementRule()) {
					sequence_FlowSpecRefinement(context, (FlowSpecification) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.GROUP_EXTENSION:
				if(context == grammarAccess.getGroupExtensionRule()) {
					sequence_GroupExtension(context, (GroupExtension) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.IMPLEMENTATION_EXTENSION:
				if(context == grammarAccess.getImplementationExtensionRule()) {
					sequence_ImplementationExtension(context, (ImplementationExtension) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.INTEGER_LITERAL:
				if(context == grammarAccess.getIntegerLitRule() ||
				   context == grammarAccess.getNumberValueRule()) {
					sequence_IntegerLit(context, (IntegerLiteral) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getConstantPropertyExpressionRule() ||
				   context == grammarAccess.getIntegerTermRule() ||
				   context == grammarAccess.getNumAltRule() ||
				   context == grammarAccess.getPropertyExpressionRule()) {
					sequence_IntegerTerm(context, (IntegerLiteral) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.LIST_TYPE:
				if(context == grammarAccess.getListTypeRule() ||
				   context == grammarAccess.getUnnamedPropertyTypeRule()) {
					sequence_ListType(context, (ListType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.LIST_VALUE:
				if(context == grammarAccess.getConstantPropertyExpressionRule() ||
				   context == grammarAccess.getListTermRule() ||
				   context == grammarAccess.getPropertyExpressionRule()) {
					sequence_ListTerm(context, (ListValue) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.MEMORY_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getMemoryImplementationRule()) {
					sequence_MemoryImplementation(context, (MemoryImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.MEMORY_PROTOTYPE:
				if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getMemoryPrototypeRule()) {
					sequence_MemoryPrototype(context, (MemoryPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPrototypeRule()) {
					sequence_MemoryPrototype_Prototype(context, (MemoryPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.MEMORY_SUBCOMPONENT:
				if(context == grammarAccess.getMemorySubcomponentRule()) {
					sequence_MemorySubcomponent(context, (MemorySubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.MEMORY_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getMemoryTypeRule()) {
					sequence_MemoryType(context, (MemoryType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.METACLASS_REFERENCE:
				if(context == grammarAccess.getAllReferenceRule()) {
					sequence_AllReference(context, (MetaclassReference) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPropertyOwnerRule() ||
				   context == grammarAccess.getQMReferenceRule()) {
					sequence_QMReference(context, (MetaclassReference) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.MODAL_PROPERTY_VALUE:
				if(context == grammarAccess.getModalPropertyValueRule()) {
					sequence_ModalPropertyValue(context, (ModalPropertyValue) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getOptionalModalPropertyValueRule()) {
					sequence_OptionalModalPropertyValue(context, (ModalPropertyValue) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPropertyValueRule()) {
					sequence_PropertyValue(context, (ModalPropertyValue) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.MODE:
				if(context == grammarAccess.getModeRule()) {
					sequence_Mode(context, (Mode) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.MODE_BINDING:
				if(context == grammarAccess.getModeRefRule()) {
					sequence_ModeRef(context, (ModeBinding) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.MODE_TRANSITION:
				if(context == grammarAccess.getModeTransitionRule()) {
					sequence_ModeTransition(context, (ModeTransition) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.MODE_TRANSITION_TRIGGER:
				if(context == grammarAccess.getTriggerRule()) {
					sequence_Trigger(context, (ModeTransitionTrigger) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.NAMED_VALUE:
				if(context == grammarAccess.getConstantValueRule() ||
				   context == grammarAccess.getNumAltRule()) {
					sequence_ConstantValue(context, (NamedValue) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getConstantPropertyExpressionRule() ||
				   context == grammarAccess.getLiteralorReferenceTermRule() ||
				   context == grammarAccess.getPropertyExpressionRule()) {
					sequence_LiteralorReferenceTerm(context, (NamedValue) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.NUMERIC_RANGE:
				if(context == grammarAccess.getIntegerRangeRule()) {
					sequence_IntegerRange(context, (NumericRange) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getRealRangeRule()) {
					sequence_RealRange(context, (NumericRange) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.OPERATION:
				if(context == grammarAccess.getNumAltRule() ||
				   context == grammarAccess.getSignedConstantRule()) {
					sequence_SignedConstant(context, (Operation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PACKAGE_RENAME:
				if(context == grammarAccess.getPackageRenameRule()) {
					sequence_PackageRename(context, (PackageRename) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getRenameAllRule()) {
					sequence_RenameAll(context, (PackageRename) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PARAMETER:
				if(context == grammarAccess.getParameterRule()) {
					sequence_Parameter(context, (Parameter) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PARAMETER_CONNECTION:
				if(context == grammarAccess.getParameterConnectionRule()) {
					sequence_ParameterConnection(context, (ParameterConnection) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PORT_CONNECTION:
				if(context == grammarAccess.getPortConnectionRule()) {
					sequence_PortConnection(context, (PortConnection) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PORT_PROXY:
				if(context == grammarAccess.getPortProxyRule() ||
				   context == grammarAccess.getProcessorFeatureRule()) {
					sequence_PortProxy(context, (PortProxy) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PORT_SPECIFICATION:
				if(context == grammarAccess.getPortSpecificationRule()) {
					sequence_PortSpecification(context, (PortSpecification) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PRIVATE_PACKAGE_SECTION:
				if(context == grammarAccess.getPrivatePackageSectionRule()) {
					sequence_PrivatePackageSection(context, (PrivatePackageSection) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROCESS_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getProcessImplementationRule()) {
					sequence_ProcessImplementation(context, (ProcessImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROCESS_PROTOTYPE:
				if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getProcessPrototypeRule()) {
					sequence_ProcessPrototype(context, (ProcessPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPrototypeRule()) {
					sequence_ProcessPrototype_Prototype(context, (ProcessPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROCESS_SUBCOMPONENT:
				if(context == grammarAccess.getProcessSubcomponentRule()) {
					sequence_ProcessSubcomponent(context, (ProcessSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROCESS_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getProcessTypeRule()) {
					sequence_ProcessType(context, (ProcessType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROCESSOR_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getProcessorImplementationRule()) {
					sequence_ProcessorImplementation(context, (ProcessorImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROCESSOR_PROTOTYPE:
				if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getProcessorPrototypeRule()) {
					sequence_ProcessorPrototype(context, (ProcessorPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPrototypeRule()) {
					sequence_ProcessorPrototype_Prototype(context, (ProcessorPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROCESSOR_SUBCOMPONENT:
				if(context == grammarAccess.getProcessorSubcomponentRule()) {
					sequence_ProcessorSubcomponent(context, (ProcessorSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROCESSOR_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getProcessorTypeRule()) {
					sequence_ProcessorType(context, (ProcessorType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROPERTY:
				if(context == grammarAccess.getPropertyDefinitionRule()) {
					sequence_PropertyDefinition(context, (Property) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROPERTY_ASSOCIATION:
				if(context == grammarAccess.getBasicPropertyAssociationRule()) {
					sequence_BasicPropertyAssociation(context, (PropertyAssociation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getContainedPropertyAssociationRule() ||
				   context == grammarAccess.getPModelRule()) {
					sequence_ContainedPropertyAssociation(context, (PropertyAssociation) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getPropertyAssociationRule()) {
					sequence_PropertyAssociation(context, (PropertyAssociation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROPERTY_CONSTANT:
				if(context == grammarAccess.getPropertyConstantRule()) {
					sequence_PropertyConstant(context, (PropertyConstant) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PROPERTY_SET:
				if(context == grammarAccess.getModelRule() ||
				   context == grammarAccess.getPropertySetRule()) {
					sequence_PropertySet(context, (PropertySet) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.PUBLIC_PACKAGE_SECTION:
				if(context == grammarAccess.getPublicPackageSectionRule()) {
					sequence_PublicPackageSection(context, (PublicPackageSection) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.RANGE_TYPE:
				if(context == grammarAccess.getPropertyTypeRule() ||
				   context == grammarAccess.getRangeTypeRule()) {
					sequence_RangeType(context, (RangeType) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnnamedPropertyTypeRule() ||
				   context == grammarAccess.getUnnamedRangeTypeRule()) {
					sequence_UnnamedRangeType(context, (RangeType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.RANGE_VALUE:
				if(context == grammarAccess.getConstantPropertyExpressionRule() ||
				   context == grammarAccess.getNumericRangeTermRule() ||
				   context == grammarAccess.getPropertyExpressionRule()) {
					sequence_NumericRangeTerm(context, (RangeValue) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.REAL_LITERAL:
				if(context == grammarAccess.getNumberValueRule() ||
				   context == grammarAccess.getRealLitRule()) {
					sequence_RealLit(context, (RealLiteral) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getConstantPropertyExpressionRule() ||
				   context == grammarAccess.getNumAltRule() ||
				   context == grammarAccess.getPropertyExpressionRule() ||
				   context == grammarAccess.getRealTermRule()) {
					sequence_RealTerm(context, (RealLiteral) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.REALIZATION:
				if(context == grammarAccess.getRealizationRule()) {
					sequence_Realization(context, (Realization) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.RECORD_TYPE:
				if(context == grammarAccess.getPropertyTypeRule() ||
				   context == grammarAccess.getRecordTypeRule()) {
					sequence_RecordType(context, (RecordType) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnnamedPropertyTypeRule() ||
				   context == grammarAccess.getUnnamedRecordTypeRule()) {
					sequence_UnnamedRecordType(context, (RecordType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.RECORD_VALUE:
				if(context == grammarAccess.getOldRecordTermRule()) {
					sequence_OldRecordTerm(context, (RecordValue) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getConstantPropertyExpressionRule() ||
				   context == grammarAccess.getPropertyExpressionRule() ||
				   context == grammarAccess.getRecordTermRule()) {
					sequence_RecordTerm(context, (RecordValue) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.REFERENCE_TYPE:
				if(context == grammarAccess.getPropertyTypeRule() ||
				   context == grammarAccess.getReferenceTypeRule()) {
					sequence_ReferenceType(context, (ReferenceType) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnnamedPropertyTypeRule() ||
				   context == grammarAccess.getUnnamedReferenceTypeRule()) {
					sequence_UnnamedReferenceType(context, (ReferenceType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.REFERENCE_VALUE:
				if(context == grammarAccess.getPropertyExpressionRule() ||
				   context == grammarAccess.getReferenceTermRule()) {
					sequence_ReferenceTerm(context, (ReferenceValue) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.STRING_LITERAL:
				if(context == grammarAccess.getConstantPropertyExpressionRule() ||
				   context == grammarAccess.getPropertyExpressionRule() ||
				   context == grammarAccess.getStringTermRule()) {
					sequence_StringTerm(context, (StringLiteral) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_ACCESS:
				if(context == grammarAccess.getSubprogramAccessRule()) {
					sequence_SubprogramAccess(context, (SubprogramAccess) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_CALL:
				if(context == grammarAccess.getSubprogramCallRule()) {
					sequence_SubprogramCall(context, (SubprogramCall) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_CALL_SEQUENCE:
				if(context == grammarAccess.getSubprogramCallSequenceRule()) {
					sequence_SubprogramCallSequence(context, (SubprogramCallSequence) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_GROUP_ACCESS:
				if(context == grammarAccess.getSubprogramGroupAccessRule()) {
					sequence_SubprogramGroupAccess(context, (SubprogramGroupAccess) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_GROUP_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getSubprogramGroupImplementationRule()) {
					sequence_SubprogramGroupImplementation(context, (SubprogramGroupImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_GROUP_PROTOTYPE:
				if(context == grammarAccess.getPrototypeRule()) {
					sequence_Prototype_SubprogramGroupPrototype(context, (SubprogramGroupPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getSubprogramGroupPrototypeRule()) {
					sequence_SubprogramGroupPrototype(context, (SubprogramGroupPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_GROUP_SUBCOMPONENT:
				if(context == grammarAccess.getSubprogramGroupSubcomponentRule()) {
					sequence_SubprogramGroupSubcomponent(context, (SubprogramGroupSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_GROUP_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getSubprogramGroupTypeRule()) {
					sequence_SubprogramGroupType(context, (SubprogramGroupType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getSubprogramImplementationRule()) {
					sequence_SubprogramImplementation(context, (SubprogramImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_PROTOTYPE:
				if(context == grammarAccess.getPrototypeRule()) {
					sequence_Prototype_SubprogramPrototype(context, (SubprogramPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getSubprogramPrototypeRule()) {
					sequence_SubprogramPrototype(context, (SubprogramPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_PROXY:
				if(context == grammarAccess.getProcessorFeatureRule() ||
				   context == grammarAccess.getSubprogramProxyRule()) {
					sequence_SubprogramProxy(context, (SubprogramProxy) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_SUBCOMPONENT:
				if(context == grammarAccess.getSubprogramSubcomponentRule()) {
					sequence_SubprogramSubcomponent(context, (SubprogramSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SUBPROGRAM_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getSubprogramTypeRule()) {
					sequence_SubprogramType(context, (SubprogramType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SYSTEM_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getSystemImplementationRule()) {
					sequence_SystemImplementation(context, (SystemImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SYSTEM_PROTOTYPE:
				if(context == grammarAccess.getPrototypeRule()) {
					sequence_Prototype_SystemPrototype(context, (SystemPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getSystemPrototypeRule()) {
					sequence_SystemPrototype(context, (SystemPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SYSTEM_SUBCOMPONENT:
				if(context == grammarAccess.getSystemSubcomponentRule()) {
					sequence_SystemSubcomponent(context, (SystemSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.SYSTEM_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getSystemTypeRule()) {
					sequence_SystemType(context, (SystemType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.THREAD_GROUP_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getThreadGroupImplementationRule()) {
					sequence_ThreadGroupImplementation(context, (ThreadGroupImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.THREAD_GROUP_PROTOTYPE:
				if(context == grammarAccess.getPrototypeRule()) {
					sequence_Prototype_ThreadGroupPrototype(context, (ThreadGroupPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getThreadGroupPrototypeRule()) {
					sequence_ThreadGroupPrototype(context, (ThreadGroupPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.THREAD_GROUP_SUBCOMPONENT:
				if(context == grammarAccess.getThreadGroupSubcomponentRule()) {
					sequence_ThreadGroupSubcomponent(context, (ThreadGroupSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.THREAD_GROUP_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getThreadGroupTypeRule()) {
					sequence_ThreadGroupType(context, (ThreadGroupType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.THREAD_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getThreadImplementationRule()) {
					sequence_ThreadImplementation(context, (ThreadImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.THREAD_PROTOTYPE:
				if(context == grammarAccess.getPrototypeRule()) {
					sequence_Prototype_ThreadPrototype(context, (ThreadPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getThreadPrototypeRule()) {
					sequence_ThreadPrototype(context, (ThreadPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.THREAD_SUBCOMPONENT:
				if(context == grammarAccess.getThreadSubcomponentRule()) {
					sequence_ThreadSubcomponent(context, (ThreadSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.THREAD_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getThreadTypeRule()) {
					sequence_ThreadType(context, (ThreadType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.TYPE_EXTENSION:
				if(context == grammarAccess.getTypeExtensionRule()) {
					sequence_TypeExtension(context, (TypeExtension) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.UNIT_LITERAL:
				if(context == grammarAccess.getUnitLiteralConversionRule()) {
					sequence_UnitLiteralConversion(context, (UnitLiteral) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnitLiteralRule()) {
					sequence_UnitLiteral(context, (UnitLiteral) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.UNITS_TYPE:
				if(context == grammarAccess.getPropertyTypeRule() ||
				   context == grammarAccess.getUnitsTypeRule()) {
					sequence_UnitsType(context, (UnitsType) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getUnnamedPropertyTypeRule() ||
				   context == grammarAccess.getUnnamedUnitsTypeRule()) {
					sequence_UnnamedUnitsType(context, (UnitsType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.VIRTUAL_BUS_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getVirtualBusImplementationRule()) {
					sequence_VirtualBusImplementation(context, (VirtualBusImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.VIRTUAL_BUS_PROTOTYPE:
				if(context == grammarAccess.getPrototypeRule()) {
					sequence_Prototype_VirtualBusPrototype(context, (VirtualBusPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getVirtualBusPrototypeRule()) {
					sequence_VirtualBusPrototype(context, (VirtualBusPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.VIRTUAL_BUS_SUBCOMPONENT:
				if(context == grammarAccess.getVirtualBusSubcomponentRule()) {
					sequence_VirtualBusSubcomponent(context, (VirtualBusSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.VIRTUAL_BUS_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getVirtualBusTypeRule()) {
					sequence_VirtualBusType(context, (VirtualBusType) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.VIRTUAL_PROCESSOR_IMPLEMENTATION:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentImplementationRule() ||
				   context == grammarAccess.getVirtualProcessorImplementationRule()) {
					sequence_VirtualProcessorImplementation(context, (VirtualProcessorImplementation) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.VIRTUAL_PROCESSOR_PROTOTYPE:
				if(context == grammarAccess.getPrototypeRule()) {
					sequence_Prototype_VirtualProcessorPrototype(context, (VirtualProcessorPrototype) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getComponentPrototypeRule() ||
				   context == grammarAccess.getVirtualProcessorPrototypeRule()) {
					sequence_VirtualProcessorPrototype(context, (VirtualProcessorPrototype) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.VIRTUAL_PROCESSOR_SUBCOMPONENT:
				if(context == grammarAccess.getVirtualProcessorSubcomponentRule()) {
					sequence_VirtualProcessorSubcomponent(context, (VirtualProcessorSubcomponent) semanticObject); 
					return; 
				}
				else break;
			case Aadl2Package.VIRTUAL_PROCESSOR_TYPE:
				if(context == grammarAccess.getClassifierRule() ||
				   context == grammarAccess.getComponentTypeRule() ||
				   context == grammarAccess.getVirtualProcessorTypeRule()) {
					sequence_VirtualProcessorType(context, (VirtualProcessorType) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (
	 *         name=PNAME 
	 *         ((ownedPublicSection=PublicPackageSection ownedPrivateSection=PrivatePackageSection?) | ownedPrivateSection=PrivatePackageSection) 
	 *         ownedPropertyAssociation+=BasicPropertyAssociation*
	 *     )
	 */
	protected void sequence_AadlPackage(EObject context, AadlPackage semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((context=[Context|ID]? connectionEnd=[ConnectionEnd|ID]) | connectionEnd=[PortProxy|ID] | connectionEnd=[InternalFeature|ID])
	 */
	protected void sequence_AbstractConnectionEnd_ConnectedElement_InternalEvent_ProcessorPort(EObject context, ConnectedElement semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[AbstractFeature|REFINEDNAME]) 
	 *         (in?='in' | out?='out')? 
	 *         featurePrototype=[FeaturePrototype|QCREF]? 
	 *         arrayDimension+=ArrayDimension? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_AbstractFeature(EObject context, AbstractFeature semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (
	 *                 ownedSystemSubcomponent+=SystemSubcomponent | 
	 *                 ownedSubprogramSubcomponent+=SubprogramSubcomponent | 
	 *                 ownedSubprogramGroupSubcomponent+=SubprogramGroupSubcomponent | 
	 *                 ownedThreadSubcomponent+=ThreadSubcomponent | 
	 *                 ownedThreadGroupSubcomponent+=ThreadGroupSubcomponent | 
	 *                 ownedProcessSubcomponent+=ProcessSubcomponent | 
	 *                 ownedProcessorSubcomponent+=ProcessorSubcomponent | 
	 *                 ownedVirtualProcessorSubcomponent+=VirtualProcessorSubcomponent | 
	 *                 ownedMemorySubcomponent+=MemorySubcomponent | 
	 *                 ownedDeviceSubcomponent+=DeviceSubcomponent | 
	 *                 ownedBusSubcomponent+=BusSubcomponent | 
	 *                 ownedVirtualBusSubcomponent+=VirtualBusSubcomponent | 
	 *                 ownedDataSubcomponent+=DataSubcomponent | 
	 *                 ownedAbstractSubcomponent+=AbstractSubcomponent
	 *             )+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (ownedPortProxy+=PortProxy | ownedSubprogramProxy+=SubprogramProxy)* 
	 *         (ownedSubprogramCallSequence+=SubprogramCallSequence+ | noCalls?='none')? 
	 *         (
	 *             (
	 *                 ownedPortConnection+=PortConnection | 
	 *                 ownedAccessConnection+=AccessConnection | 
	 *                 ownedFeatureGroupConnection+=FeatureGroupConnection | 
	 *                 ownedFeatureConnection+=FeatureConnection | 
	 *                 ownedParameterConnection+=ParameterConnection
	 *             )+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedFlowImplementation+=FlowImplementation | ownedEndToEndFlow+=EndToEndFlow)+ | noFlows?='none')? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_AbstractImplementation(EObject context, AbstractImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_AbstractPrototype(EObject context, AbstractPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_AbstractPrototype_Prototype(EObject context, AbstractPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[AbstractSubcomponent|REFINEDNAME]) 
	 *         (abstractSubcomponentType=[AbstractSubcomponentType|QCREF] (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_AbstractSubcomponent(EObject context, AbstractSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedDataAccess+=DataAccess | 
	 *                 ownedBusAccess+=BusAccess | 
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_AbstractType(EObject context, AbstractType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((context=[Context|ID]? connectionEnd=[ConnectionEnd|ID]) | connectionEnd=[SubprogramProxy|ID])
	 */
	protected void sequence_AccessConnectionEnd_ConnectedElement_ProcessorSubprogram(EObject context, ConnectedElement semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (
	 *             (name=ID accessCategory=AccessCategory source=AccessConnectionEnd bidirectional?='<->'? destination=AccessConnectionEnd) | 
	 *             (refined=[AccessConnection|REFINEDNAME] accessCategory=AccessCategory)
	 *         ) 
	 *         ownedPropertyAssociation+=PropertyAssociation* 
	 *         (inModeOrTransition+=[ModeFeature|ID] inModeOrTransition+=[ModeFeature|ID]*)?
	 *     )
	 */
	protected void sequence_AccessConnection(EObject context, AccessConnection semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (kind=AccessDirection category=AccessCategory classifier=[ComponentClassifier|QCREF]?)
	 */
	protected void sequence_AccessSpecification(EObject context, AccessSpecification semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     metaclassName+='all'
	 */
	protected void sequence_AllReference(EObject context, MetaclassReference semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (size=ArraySize?)
	 */
	protected void sequence_ArrayDimension(EObject context, ArrayDimension semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (size=INTVALUE | sizeProperty=[ArraySizeProperty|QPREF])
	 */
	protected void sequence_ArraySize(EObject context, ArraySize semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_BooleanType(EObject context, AadlBoolean semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Feature|REFINEDNAME]) 
	 *         kind=AccessDirection 
	 *         busFeatureClassifier=[BusSubcomponentType|QCREF]? 
	 *         arrayDimension+=ArrayDimension? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_BusAccess(EObject context, BusAccess semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         ((ownedAbstractSubcomponent+=AbstractSubcomponent | ownedVirtualBusSubcomponent+=VirtualBusSubcomponent)+ | noSubcomponents?='none')? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_BusImplementation(EObject context, BusImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_BusPrototype(EObject context, BusPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_BusPrototype_Prototype(EObject context, BusPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (busSubcomponentType=[BusSubcomponentType|QCREF] (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_BusSubcomponent(EObject context, BusSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedBusAccess+=BusAccess | 
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_BusType(EObject context, BusType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID? category=ComponentCategory renamedComponentType=[ComponentType|QCREF])
	 */
	protected void sequence_CTRename(EObject context, ComponentTypeRename semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID (classifierReference+=QMReference classifierReference+=QMReference*)?)
	 */
	protected void sequence_ClassifierType(EObject context, ClassifierType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (implementation=[ComponentImplementation|QCREF] (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)
	 */
	protected void sequence_ComponentImplementationReference(EObject context, ComponentImplementationReference semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (formal=[Prototype|ID] (actual+=ComponentReference | (actual+=ComponentReference actual+=ComponentReference*)))
	 */
	protected void sequence_ComponentPrototypeBinding(EObject context, ComponentPrototypeBinding semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (category=ComponentCategory subcomponentType=[SubcomponentType|QCREF] (binding+=PrototypeBinding binding+=PrototypeBinding*)?)
	 */
	protected void sequence_ComponentReference(EObject context, ComponentPrototypeActual semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (context=[Context|ID]? connectionEnd=[ConnectionEnd|ID])
	 */
	protected void sequence_ConnectedElement(EObject context, ConnectedElement semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((context=[Context|ID]? connectionEnd=[ConnectionEnd|ID]) | connectionEnd=[PortProxy|ID])
	 */
	protected void sequence_ConnectedElement_ProcessorConnectionEnd_ProcessorPort(EObject context, ConnectedElement semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     flowElement=[Connection|ID]
	 */
	protected void sequence_ConnectionFlow(EObject context, FlowSegment semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Feature|REFINEDNAME]) 
	 *         kind=AccessDirection 
	 *         dataFeatureClassifier=[DataSubcomponentType|QCREF]? 
	 *         arrayDimension+=ArrayDimension? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_DataAccess(EObject context, DataAccess semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (ownedAbstractSubcomponent+=AbstractSubcomponent | ownedDataSubcomponent+=DataSubcomponent | ownedSubprogramSubcomponent+=SubprogramSubcomponent)+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (
	 *             (ownedAccessConnection+=AccessConnection | ownedFeatureGroupConnection+=FeatureGroupConnection | ownedFeatureConnection+=FeatureConnection)+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedFlowImplementation+=FlowImplementation | ownedEndToEndFlow+=EndToEndFlow)+ | noFlows?='none')? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_DataImplementation(EObject context, DataImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Feature|REFINEDNAME]) 
	 *         ((in?='in' out?='out'?) | out?='out') 
	 *         dataFeatureClassifier=[DataSubcomponentType|QCREF]? 
	 *         arrayDimension+=ArrayDimension? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_DataPort(EObject context, DataPort semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_DataPrototype(EObject context, DataPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_DataPrototype_Prototype(EObject context, DataPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (dataSubcomponentType=[DataSubcomponentType|QCREF] (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_DataSubcomponent(EObject context, DataSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedDataAccess+=DataAccess | 
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_DataType(EObject context, DataType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID sourceText=ANNEXTEXT)
	 */
	protected void sequence_DefaultAnnexLibrary(EObject context, DefaultAnnexLibrary semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID sourceText=ANNEXTEXT (inMode+=[Mode|ID] inMode+=[Mode|ID]*)?)
	 */
	protected void sequence_DefaultAnnexSubclause(EObject context, DefaultAnnexSubclause semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (
	 *                 ownedAbstractSubcomponent+=AbstractSubcomponent | 
	 *                 ownedDataSubcomponent+=DataSubcomponent | 
	 *                 ownedVirtualBusSubcomponent+=VirtualBusSubcomponent | 
	 *                 ownedBusSubcomponent+=BusSubcomponent
	 *             )+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (ownedPortProxy+=PortProxy | ownedSubprogramProxy+=SubprogramProxy)* 
	 *         (
	 *             (
	 *                 ownedPortConnection+=PortConnection | 
	 *                 ownedAccessConnection+=AccessConnection | 
	 *                 ownedFeatureGroupConnection+=FeatureGroupConnection | 
	 *                 ownedFeatureConnection+=FeatureConnection
	 *             )+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedFlowImplementation+=FlowImplementation | ownedEndToEndFlow+=EndToEndFlow)+ | noFlows?='none')? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_DeviceImplementation(EObject context, DeviceImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_DevicePrototype(EObject context, DevicePrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_DevicePrototype_Prototype(EObject context, DevicePrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (deviceSubcomponentType=[DeviceSubcomponentType|QCREF] (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_DeviceSubcomponent(EObject context, DeviceSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedBusAccess+=BusAccess | 
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_DeviceType(EObject context, DeviceType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     flowElement=[Connection|ID]
	 */
	protected void sequence_ETEConnectionFlow(EObject context, EndToEndFlowSegment semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (context=[Subcomponent|ID]? flowElement=[EndToEndFlowElement|ID])
	 */
	protected void sequence_ETESubcomponentFlow(EObject context, EndToEndFlowSegment semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (
	 *             (
	 *                 name=ID 
	 *                 ownedEndToEndFlowSegment+=ETESubcomponentFlow 
	 *                 (ownedEndToEndFlowSegment+=ETEConnectionFlow ownedEndToEndFlowSegment+=ETESubcomponentFlow)+
	 *             ) | 
	 *             refined=[EndToEndFlow|REFINEDNAME]
	 *         ) 
	 *         ownedPropertyAssociation+=PropertyAssociation* 
	 *         (inModeOrTransition+=[ModeFeature|ID] inModeOrTransition+=[ModeFeature|ID]*)?
	 *     )
	 */
	protected void sequence_EndToEndFlow(EObject context, EndToEndFlow semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_EnumerationLiteral(EObject context, EnumerationLiteral semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID ownedLiteral+=EnumerationLiteral ownedLiteral+=EnumerationLiteral*)
	 */
	protected void sequence_EnumerationType(EObject context, EnumerationType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Feature|REFINEDNAME]) 
	 *         ((in?='in' out?='out'?) | out?='out') 
	 *         dataFeatureClassifier=[DataSubcomponentType|QCREF]? 
	 *         arrayDimension+=ArrayDimension? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_EventDataPort(EObject context, EventDataPort semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID dataClassifier=[DataClassifier|QCREF]? ownedPropertyAssociation+=PropertyAssociation*)
	 */
	protected void sequence_EventDataSource(EObject context, EventDataSource semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Feature|REFINEDNAME]) 
	 *         ((in?='in' out?='out'?) | out?='out') 
	 *         arrayDimension+=ArrayDimension? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_EventPort(EObject context, EventPort semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID ownedPropertyAssociation+=PropertyAssociation*)
	 */
	protected void sequence_EventSource(EObject context, EventSource semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID? renamedFeatureGroupType=[FeatureGroupType|QCREF])
	 */
	protected void sequence_FGTRename(EObject context, FeatureGroupTypeRename semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ((name=ID source=ConnectedElement bidirectional?='<->'? destination=ConnectedElement) | refined=[FeatureConnection|REFINEDNAME]) 
	 *         ownedPropertyAssociation+=PropertyAssociation* 
	 *         (inModeOrTransition+=[ModeFeature|ID] inModeOrTransition+=[ModeFeature|ID]*)?
	 *     )
	 */
	protected void sequence_FeatureConnection(EObject context, FeatureConnection semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ((name=ID source=ConnectedElement bidirectional?='<->'? destination=ConnectedElement) | refined=[FeatureGroupConnection|REFINEDNAME]) 
	 *         ownedPropertyAssociation+=PropertyAssociation* 
	 *         (inModeOrTransition+=[ModeFeature|ID] inModeOrTransition+=[ModeFeature|ID]*)?
	 *     )
	 */
	protected void sequence_FeatureGroupConnection(EObject context, FeatureGroupConnection semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (featureType=[FeatureType|QCREF] (binding+=PrototypeBinding binding+=PrototypeBinding*)?)
	 */
	protected void sequence_FeatureGroupPrototypeActual(EObject context, FeatureGroupPrototypeActual semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (formal=[Prototype|ID] actual=FeatureGroupPrototypeActual)
	 */
	protected void sequence_FeatureGroupPrototypeBinding(EObject context, FeatureGroupPrototypeBinding semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((name=ID | refined=[FeatureGroupPrototype|REFINEDNAME]) constrainingFeatureGroupType=[FeatureGroupType|QCREF]?)
	 */
	protected void sequence_FeatureGroupPrototype(EObject context, FeatureGroupPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[FeatureGroupPrototype|REFINEDNAME]) 
	 *         constrainingFeatureGroupType=[FeatureGroupType|QCREF]? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_FeatureGroupPrototype_Prototype(EObject context, FeatureGroupPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=GroupExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             ownedDataPort+=DataPort | 
	 *             ownedEventPort+=EventPort | 
	 *             ownedEventDataPort+=EventDataPort | 
	 *             ownedFeatureGroup+=FeatureGroup | 
	 *             ownedDataAccess+=DataAccess | 
	 *             ownedBusAccess+=BusAccess | 
	 *             ownedSubprogramAccess+=SubprogramAccess | 
	 *             ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *             ownedAbstractFeature+=AbstractFeature | 
	 *             ownedParameter+=Parameter
	 *         )* 
	 *         inverse=[FeatureGroupType|QCREF]? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_FeatureGroupType(EObject context, FeatureGroupType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Feature|REFINEDNAME]) 
	 *         (in?='in' | out?='out')? 
	 *         (inverse?='inverse'? featureType=[FeatureType|QCREF])? 
	 *         arrayDimension+=ArrayDimension? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation*
	 *     )
	 */
	protected void sequence_FeatureGroup(EObject context, FeatureGroup semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (formal=[Prototype|ID] (actual=PortSpecification | actual=AccessSpecification | actual=FeaturePrototypeReference))
	 */
	protected void sequence_FeaturePrototypeBinding(EObject context, FeaturePrototypeBinding semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((in?='in' | out?='out')? prototype=[FeaturePrototype|ID])
	 */
	protected void sequence_FeaturePrototypeReference(EObject context, FeaturePrototypeReference semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((name=ID | refined=[FeaturePrototype|REFINEDNAME]) (in?='in' | out?='out')? constrainingClassifier=[ComponentClassifier|QCREF]?)
	 */
	protected void sequence_FeaturePrototype(EObject context, FeaturePrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[FeaturePrototype|REFINEDNAME]) 
	 *         (in?='in' | out?='out')? 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_FeaturePrototype_Prototype(EObject context, FeaturePrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (context=[Context|ID]? feature=[Feature|ID])
	 */
	protected void sequence_FlowEnd(EObject context, FlowEnd semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (
	 *             (specification=[FlowSpecification|ID] kind=FlowSource (ownedFlowSegment+=SubcomponentFlow ownedFlowSegment+=ConnectionFlow)*) | 
	 *             (specification=[FlowSpecification|ID] kind=FlowSink (ownedFlowSegment+=ConnectionFlow ownedFlowSegment+=SubcomponentFlow)*) | 
	 *             (
	 *                 specification=[FlowSpecification|ID] 
	 *                 kind=FlowPath 
	 *                 ((ownedFlowSegment+=ConnectionFlow ownedFlowSegment+=SubcomponentFlow)* ownedFlowSegment+=ConnectionFlow)?
	 *             )
	 *         ) 
	 *         ownedPropertyAssociation+=PropertyAssociation* 
	 *         (inModeOrTransition+=[ModeFeature|ID] inModeOrTransition+=[ModeFeature|ID]*)?
	 *     )
	 */
	protected void sequence_FlowImplementation_FlowPathImpl_FlowSinkImpl_FlowSourceImpl(EObject context, FlowImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         specification=[FlowSpecification|ID] 
	 *         kind=FlowPath 
	 *         ((ownedFlowSegment+=ConnectionFlow ownedFlowSegment+=SubcomponentFlow)* ownedFlowSegment+=ConnectionFlow)?
	 *     )
	 */
	protected void sequence_FlowPathImpl(EObject context, FlowImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (
	 *             (name=ID kind=FlowSource outEnd=FlowEnd) | 
	 *             (name=ID kind=FlowSink InEnd=FlowEnd) | 
	 *             (name=ID kind=FlowPath InEnd=FlowEnd outEnd=FlowEnd) | 
	 *             (refined=[FlowSpecification|REFINEDNAME] kind=FlowKind)
	 *         ) 
	 *         ownedPropertyAssociation+=PropertyAssociation* 
	 *         (inModeOrTransition+=[ModeFeature|ID] inModeOrTransition+=[ModeFeature|ID]*)?
	 *     )
	 */
	protected void sequence_FlowPathSpec_FlowSinkSpec_FlowSourceSpec_FlowSpecRefinement_FlowSpecification(EObject context, FlowSpecification semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID kind=FlowPath InEnd=FlowEnd outEnd=FlowEnd)
	 */
	protected void sequence_FlowPathSpec(EObject context, FlowSpecification semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (specification=[FlowSpecification|ID] kind=FlowSink (ownedFlowSegment+=ConnectionFlow ownedFlowSegment+=SubcomponentFlow)*)
	 */
	protected void sequence_FlowSinkImpl(EObject context, FlowImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID kind=FlowSink InEnd=FlowEnd)
	 */
	protected void sequence_FlowSinkSpec(EObject context, FlowSpecification semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (specification=[FlowSpecification|ID] kind=FlowSource (ownedFlowSegment+=SubcomponentFlow ownedFlowSegment+=ConnectionFlow)*)
	 */
	protected void sequence_FlowSourceImpl(EObject context, FlowImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID kind=FlowSource outEnd=FlowEnd)
	 */
	protected void sequence_FlowSourceSpec(EObject context, FlowSpecification semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (refined=[FlowSpecification|REFINEDNAME] kind=FlowKind)
	 */
	protected void sequence_FlowSpecRefinement(EObject context, FlowSpecification semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     extended=[FeatureGroupType|QCREF]
	 */
	protected void sequence_GroupExtension(EObject context, GroupExtension semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     extended=[ComponentImplementation|QCREF]
	 */
	protected void sequence_ImplementationExtension(EObject context, ImplementationExtension semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=SignedInt
	 */
	protected void sequence_IntegerLit(EObject context, IntegerLiteral semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (lowerBound=IntegerTerm | lowerBound=SignedConstant | lowerBound=ConstantValue) 
	 *         (upperBound=IntegerTerm | upperBound=SignedConstant | upperBound=ConstantValue)
	 *     )
	 */
	protected void sequence_IntegerRange(EObject context, NumericRange semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID range=IntegerRange? (ownedUnitsType=UnnamedUnitsType | referencedUnitsType=[UnitsType|QPREF])?)
	 */
	protected void sequence_IntegerType(EObject context, AadlInteger semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     connectionEnd=[InternalFeature|ID]
	 */
	protected void sequence_InternalEvent(EObject context, ConnectedElement semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (referencedElementType=[PropertyType|QPREF] | ownedElementType=UnnamedPropertyType)
	 */
	protected void sequence_ListType(EObject context, ListType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (ownedAbstractSubcomponent+=AbstractSubcomponent | ownedMemorySubcomponent+=MemorySubcomponent | ownedBusSubcomponent+=BusSubcomponent)+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (
	 *             (ownedAccessConnection+=AccessConnection | ownedFeatureGroupConnection+=FeatureGroupConnection | ownedFeatureConnection+=FeatureConnection)+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_MemoryImplementation(EObject context, MemoryImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_MemoryPrototype(EObject context, MemoryPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_MemoryPrototype_Prototype(EObject context, MemoryPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (memorySubcomponentType=[MemorySubcomponentType|QCREF] (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_MemorySubcomponent(EObject context, MemorySubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedBusAccess+=BusAccess | 
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_MemoryType(EObject context, MemoryType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (parentMode=[Mode|ID] derivedMode=[Mode|ID]?)
	 */
	protected void sequence_ModeRef(EObject context, ModeBinding semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID? 
	 *         source=[Mode|ID] 
	 *         ownedTrigger+=Trigger 
	 *         ownedTrigger+=Trigger* 
	 *         destination=[Mode|ID] 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_ModeTransition(EObject context, ModeTransition semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID initial?='initial'? ownedPropertyAssociation+=PropertyAssociation*)
	 */
	protected void sequence_Mode(EObject context, Mode semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID renamedPackage=[AadlPackage|PNAME] renameAll?='all'?)
	 */
	protected void sequence_PackageRename(EObject context, PackageRename semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ((name=ID source=ConnectedElement destination=ConnectedElement) | refined=[ParameterConnection|REFINEDNAME]) 
	 *         ownedPropertyAssociation+=PropertyAssociation* 
	 *         (inModeOrTransition+=[ModeFeature|ID] inModeOrTransition+=[ModeFeature|ID]*)?
	 *     )
	 */
	protected void sequence_ParameterConnection(EObject context, ParameterConnection semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Feature|REFINEDNAME]) 
	 *         ((in?='in' out?='out'?) | out?='out') 
	 *         dataFeatureClassifier=[DataSubcomponentType|QCREF]? 
	 *         arrayDimension+=ArrayDimension? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_Parameter(EObject context, Parameter semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ((name=ID source=AbstractConnectionEnd bidirectional?='<->'? destination=ProcessorConnectionEnd) | refined=[PortConnection|REFINEDNAME]) 
	 *         ownedPropertyAssociation+=PropertyAssociation* 
	 *         (inModeOrTransition+=[ModeFeature|ID] inModeOrTransition+=[ModeFeature|ID]*)?
	 *     )
	 */
	protected void sequence_PortConnection(EObject context, PortConnection semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID dataClassifier=[DataClassifier|QCREF]? ownedPropertyAssociation+=PropertyAssociation*)
	 */
	protected void sequence_PortProxy(EObject context, PortProxy semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (((in?='in' out?='out'?) | out?='out') category=PortCategory classifier=[ComponentClassifier|QCREF]?)
	 */
	protected void sequence_PortSpecification(EObject context, PortSpecification semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (
	 *             (importedUnit+=[ModelUnit|PNAME] importedUnit+=[ModelUnit|PNAME]*) | 
	 *             ownedPackageRename+=PackageRename | 
	 *             ownedPackageRename+=RenameAll | 
	 *             ownedFeatureGroupTypeRename+=FGTRename | 
	 *             ownedComponentTypeRename+=CTRename
	 *         )* 
	 *         (ownedClassifier+=Classifier | ownedAnnexLibrary+=AnnexLibrary)*
	 *     )
	 */
	protected void sequence_PrivatePackageSection(EObject context, PrivatePackageSection semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (
	 *                 ownedThreadGroupSubcomponent+=ThreadGroupSubcomponent | 
	 *                 ownedThreadSubcomponent+=ThreadSubcomponent | 
	 *                 ownedSubprogramSubcomponent+=SubprogramSubcomponent | 
	 *                 ownedSubprogramGroupSubcomponent+=SubprogramGroupSubcomponent | 
	 *                 ownedDataSubcomponent+=DataSubcomponent | 
	 *                 ownedAbstractSubcomponent+=AbstractSubcomponent
	 *             )+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (ownedPortProxy+=PortProxy | ownedSubprogramProxy+=SubprogramProxy)* 
	 *         (
	 *             (
	 *                 ownedPortConnection+=PortConnection | 
	 *                 ownedAccessConnection+=AccessConnection | 
	 *                 ownedFeatureGroupConnection+=FeatureGroupConnection | 
	 *                 ownedFeatureConnection+=FeatureConnection
	 *             )+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedFlowImplementation+=FlowImplementation | ownedEndToEndFlow+=EndToEndFlow)+ | noFlows?='none')? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_ProcessImplementation(EObject context, ProcessImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_ProcessPrototype(EObject context, ProcessPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_ProcessPrototype_Prototype(EObject context, ProcessPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (processSubcomponentType=[ProcessSubcomponentType|QCREF] (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_ProcessSubcomponent(EObject context, ProcessSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedDataAccess+=DataAccess | 
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_ProcessType(EObject context, ProcessType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (
	 *                 ownedMemorySubcomponent+=MemorySubcomponent | 
	 *                 ownedAbstractSubcomponent+=AbstractSubcomponent | 
	 *                 ownedBusSubcomponent+=BusSubcomponent | 
	 *                 ownedVirtualBusSubcomponent+=VirtualBusSubcomponent | 
	 *                 ownedVirtualProcessorSubcomponent+=VirtualProcessorSubcomponent
	 *             )+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (
	 *             (
	 *                 ownedPortConnection+=PortConnection | 
	 *                 ownedAccessConnection+=AccessConnection | 
	 *                 ownedFeatureGroupConnection+=FeatureGroupConnection | 
	 *                 ownedFeatureConnection+=FeatureConnection
	 *             )+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedFlowImplementation+=FlowImplementation | ownedEndToEndFlow+=EndToEndFlow)+ | noFlows?='none')? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_ProcessorImplementation(EObject context, ProcessorImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     connectionEnd=[PortProxy|ID]
	 */
	protected void sequence_ProcessorPort(EObject context, ConnectedElement semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_ProcessorPrototype(EObject context, ProcessorPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_ProcessorPrototype_Prototype(EObject context, ProcessorPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (
	 *             processorSubcomponentType=[ProcessorSubcomponentType|QCREF] 
	 *             (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?
	 *         )? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_ProcessorSubcomponent(EObject context, ProcessorSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     connectionEnd=[SubprogramProxy|ID]
	 */
	protected void sequence_ProcessorSubprogram(EObject context, ConnectedElement semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedBusAccess+=BusAccess | 
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_ProcessorType(EObject context, ProcessorType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID (referencedPropertyType=[PropertyType|QPREF] | ownedPropertyType=UnnamedPropertyType) constantValue=ConstantPropertyExpression)
	 */
	protected void sequence_PropertyConstant(EObject context, PropertyConstant semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         inherit?='inherit'? 
	 *         (referencedPropertyType=[PropertyType|QPREF] | ownedPropertyType=UnnamedPropertyType) 
	 *         defaultValue=PropertyExpression? 
	 *         ((appliesTo+=PropertyOwner appliesTo+=PropertyOwner*) | appliesTo+=AllReference)
	 *     )
	 */
	protected void sequence_PropertyDefinition(EObject context, Property semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (importedUnit+=[PropertySet|ID] importedUnit+=[PropertySet|ID]*)* 
	 *         (ownedPropertyType+=PropertyType | ownedProperty+=PropertyDefinition | ownedPropertyConstant+=PropertyConstant)* 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_PropertySet(EObject context, PropertySet semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_Prototype_SubprogramGroupPrototype(EObject context, SubprogramGroupPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_Prototype_SubprogramPrototype(EObject context, SubprogramPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_Prototype_SystemPrototype(EObject context, SystemPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_Prototype_ThreadGroupPrototype(EObject context, ThreadGroupPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_Prototype_ThreadPrototype(EObject context, ThreadPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_Prototype_VirtualBusPrototype(EObject context, VirtualBusPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[ComponentPrototype|REFINEDNAME]) 
	 *         constrainingClassifier=[ComponentClassifier|QCREF]? 
	 *         array?='['? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_Prototype_VirtualProcessorPrototype(EObject context, VirtualProcessorPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (
	 *             (importedUnit+=[ModelUnit|PNAME] importedUnit+=[ModelUnit|PNAME]*) | 
	 *             ownedPackageRename+=PackageRename | 
	 *             ownedPackageRename+=RenameAll | 
	 *             ownedFeatureGroupTypeRename+=FGTRename | 
	 *             ownedComponentTypeRename+=CTRename
	 *         )* 
	 *         (ownedClassifier+=Classifier | ownedAnnexLibrary+=AnnexLibrary)*
	 *     )
	 */
	protected void sequence_PublicPackageSection(EObject context, PublicPackageSection semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     classifier=[ComponentClassifier|FQCREF]
	 */
	protected void sequence_QCReference(EObject context, ClassifierValue semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (annexName=ID? (metaclassName+=CoreKeyWord | metaclassName+=ID)+)
	 */
	protected void sequence_QMReference(EObject context, MetaclassReference semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID (ownedNumberType=UnnamedIntegerType | ownedNumberType=UnnamedRealType | numberType=[NumberType|QPREF]))
	 */
	protected void sequence_RangeType(EObject context, RangeType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     value=SignedReal
	 */
	protected void sequence_RealLit(EObject context, RealLiteral semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (lowerBound=RealTerm | lowerBound=SignedConstant | lowerBound=ConstantValue) 
	 *         (upperBound=RealTerm | upperBound=SignedConstant | upperBound=ConstantValue)
	 *     )
	 */
	protected void sequence_RealRange(EObject context, NumericRange semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID range=RealRange? (ownedUnitsType=UnnamedUnitsType | referencedUnitsType=[UnitsType|QPREF])?)
	 */
	protected void sequence_RealType(EObject context, AadlReal semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     implemented=[ComponentType|ID]
	 */
	protected void sequence_Realization(EObject context, Realization semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID (referencedPropertyType=[PropertyType|QPREF] | ownedPropertyType=UnnamedPropertyType))
	 */
	protected void sequence_RecordField(EObject context, BasicProperty semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID ownedField+=RecordField+)
	 */
	protected void sequence_RecordType(EObject context, RecordType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID (namedElementReference+=QMReference namedElementReference+=QMReference*)?)
	 */
	protected void sequence_ReferenceType(EObject context, ReferenceType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (renamedPackage=[AadlPackage|PNAME] renameAll?='all')
	 */
	protected void sequence_RenameAll(EObject context, PackageRename semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_StringType(EObject context, AadlString semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (context=[Subcomponent|ID]? flowElement=[FlowElement|ID])
	 */
	protected void sequence_SubcomponentFlow(EObject context, FlowSegment semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Feature|REFINEDNAME]) 
	 *         kind=AccessDirection 
	 *         subprogramFeatureClassifier=[SubprogramSubcomponentType|QCREF]? 
	 *         arrayDimension+=ArrayDimension? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_SubprogramAccess(EObject context, SubprogramAccess semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID ownedSubprogramCall+=SubprogramCall+ ownedPropertyAssociation+=PropertyAssociation* (inMode+=[Mode|ID] inMode+=[Mode|ID]*)?)
	 */
	protected void sequence_SubprogramCallSequence(EObject context, SubprogramCallSequence semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (
	 *             (context=[CallContext|PNAME] calledSubprogram=[CalledSubprogram|ID]) | 
	 *             calledSubprogram=[CalledSubprogram|PNAME] | 
	 *             calledSubprogram=[SubprogramProxy|ID]
	 *         ) 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_SubprogramCall(EObject context, SubprogramCall semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Feature|REFINEDNAME]) 
	 *         kind=AccessDirection 
	 *         subprogramGroupFeatureClassifier=[SubprogramGroupSubcomponentType|QCREF]? 
	 *         arrayDimension+=ArrayDimension? 
	 *         ownedPropertyAssociation+=PropertyAssociation*
	 *     )
	 */
	protected void sequence_SubprogramGroupAccess(EObject context, SubprogramGroupAccess semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (
	 *                 ownedSubprogramSubcomponent+=SubprogramSubcomponent | 
	 *                 ownedAbstractSubcomponent+=AbstractSubcomponent | 
	 *                 ownedSubprogramGroupSubcomponent+=SubprogramGroupSubcomponent | 
	 *                 ownedDataSubcomponent+=DataSubcomponent
	 *             )+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (ownedPortProxy+=PortProxy | ownedSubprogramProxy+=SubprogramProxy)* 
	 *         (
	 *             (ownedAccessConnection+=AccessConnection | ownedFeatureGroupConnection+=FeatureGroupConnection | ownedFeatureConnection+=FeatureConnection)+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_SubprogramGroupImplementation(EObject context, SubprogramGroupImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_SubprogramGroupPrototype(EObject context, SubprogramGroupPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (
	 *             subprogramGroupSubcomponentType=[SubprogramGroupSubcomponentType|QCREF] 
	 *             (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?
	 *         )? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_SubprogramGroupSubcomponent(EObject context, SubprogramGroupSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_SubprogramGroupType(EObject context, SubprogramGroupType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (ownedAbstractSubcomponent+=AbstractSubcomponent | ownedSubprogramSubcomponent+=SubprogramSubcomponent | ownedDataSubcomponent+=DataSubcomponent)+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (ownedPortProxy+=PortProxy | ownedSubprogramProxy+=SubprogramProxy)* 
	 *         (ownedSubprogramCallSequence+=SubprogramCallSequence+ | noCalls?='none')? 
	 *         (
	 *             (
	 *                 ownedPortConnection+=PortConnection | 
	 *                 ownedAccessConnection+=AccessConnection | 
	 *                 ownedFeatureGroupConnection+=FeatureGroupConnection | 
	 *                 ownedFeatureConnection+=FeatureConnection | 
	 *                 ownedParameterConnection+=ParameterConnection
	 *             )+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedFlowImplementation+=FlowImplementation | ownedEndToEndFlow+=EndToEndFlow)+ | noFlows?='none')? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_SubprogramImplementation(EObject context, SubprogramImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_SubprogramPrototype(EObject context, SubprogramPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID subprogramClassifier=[SubprogramClassifier|QCREF]? ownedPropertyAssociation+=PropertyAssociation*)
	 */
	protected void sequence_SubprogramProxy(EObject context, SubprogramProxy semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (
	 *             subprogramSubcomponentType=[SubprogramSubcomponentType|QCREF] 
	 *             (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?
	 *         )? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_SubprogramSubcomponent(EObject context, SubprogramSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedParameter+=Parameter | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedDataAccess+=DataAccess | 
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_SubprogramType(EObject context, SubprogramType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (
	 *                 ownedSystemSubcomponent+=SystemSubcomponent | 
	 *                 ownedSubprogramSubcomponent+=SubprogramSubcomponent | 
	 *                 ownedSubprogramGroupSubcomponent+=SubprogramGroupSubcomponent | 
	 *                 ownedProcessSubcomponent+=ProcessSubcomponent | 
	 *                 ownedProcessorSubcomponent+=ProcessorSubcomponent | 
	 *                 ownedVirtualProcessorSubcomponent+=VirtualProcessorSubcomponent | 
	 *                 ownedMemorySubcomponent+=MemorySubcomponent | 
	 *                 ownedDeviceSubcomponent+=DeviceSubcomponent | 
	 *                 ownedBusSubcomponent+=BusSubcomponent | 
	 *                 ownedVirtualBusSubcomponent+=VirtualBusSubcomponent | 
	 *                 ownedDataSubcomponent+=DataSubcomponent | 
	 *                 ownedAbstractSubcomponent+=AbstractSubcomponent
	 *             )+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (ownedPortProxy+=PortProxy | ownedSubprogramProxy+=SubprogramProxy)* 
	 *         (
	 *             (
	 *                 ownedPortConnection+=PortConnection | 
	 *                 ownedAccessConnection+=AccessConnection | 
	 *                 ownedFeatureGroupConnection+=FeatureGroupConnection | 
	 *                 ownedFeatureConnection+=FeatureConnection
	 *             )+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedFlowImplementation+=FlowImplementation | ownedEndToEndFlow+=EndToEndFlow)+ | noFlows?='none')? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_SystemImplementation(EObject context, SystemImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_SystemPrototype(EObject context, SystemPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (systemSubcomponentType=[SystemSubcomponentType|QCREF] (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_SystemSubcomponent(EObject context, SystemSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedDataAccess+=DataAccess | 
	 *                 ownedBusAccess+=BusAccess | 
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+ | 
	 *             noFeatures?='none'
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_SystemType(EObject context, SystemType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (
	 *                 ownedThreadGroupSubcomponent+=ThreadGroupSubcomponent | 
	 *                 ownedThreadSubcomponent+=ThreadSubcomponent | 
	 *                 ownedSubprogramSubcomponent+=SubprogramSubcomponent | 
	 *                 ownedSubprogramGroupSubcomponent+=SubprogramGroupSubcomponent | 
	 *                 ownedDataSubcomponent+=DataSubcomponent | 
	 *                 ownedAbstractSubcomponent+=AbstractSubcomponent
	 *             )+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (ownedPortProxy+=PortProxy | ownedSubprogramProxy+=SubprogramProxy)* 
	 *         (
	 *             (
	 *                 ownedPortConnection+=PortConnection | 
	 *                 ownedAccessConnection+=AccessConnection | 
	 *                 ownedFeatureGroupConnection+=FeatureGroupConnection | 
	 *                 ownedFeatureConnection+=FeatureConnection
	 *             )+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedFlowImplementation+=FlowImplementation | ownedEndToEndFlow+=EndToEndFlow)+ | noFlows?='none')? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_ThreadGroupImplementation(EObject context, ThreadGroupImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_ThreadGroupPrototype(EObject context, ThreadGroupPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (
	 *             threadGroupSubcomponentType=[ThreadGroupSubcomponentType|QCREF] 
	 *             (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?
	 *         )? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_ThreadGroupSubcomponent(EObject context, ThreadGroupSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedDataAccess+=DataAccess | 
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_ThreadGroupType(EObject context, ThreadGroupType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (
	 *                 ownedSubprogramSubcomponent+=SubprogramSubcomponent | 
	 *                 ownedSubprogramGroupSubcomponent+=SubprogramGroupSubcomponent | 
	 *                 ownedDataSubcomponent+=DataSubcomponent | 
	 *                 ownedAbstractSubcomponent+=AbstractSubcomponent
	 *             )+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (ownedPortProxy+=PortProxy | ownedSubprogramProxy+=SubprogramProxy)* 
	 *         (ownedSubprogramCallSequence+=SubprogramCallSequence+ | noCalls?='none')? 
	 *         (
	 *             (
	 *                 ownedPortConnection+=PortConnection | 
	 *                 ownedAccessConnection+=AccessConnection | 
	 *                 ownedFeatureGroupConnection+=FeatureGroupConnection | 
	 *                 ownedFeatureConnection+=FeatureConnection | 
	 *                 ownedParameterConnection+=ParameterConnection
	 *             )+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedFlowImplementation+=FlowImplementation | ownedEndToEndFlow+=EndToEndFlow)+ | noFlows?='none')? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_ThreadImplementation(EObject context, ThreadImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_ThreadPrototype(EObject context, ThreadPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (threadSubcomponentType=[ThreadSubcomponentType|QCREF] (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_ThreadSubcomponent(EObject context, ThreadSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedDataAccess+=DataAccess | 
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_ThreadType(EObject context, ThreadType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((context=[Context|ID]? triggerPort=[Port|ID]) | triggerPort=[InternalFeature|ID] | triggerPort=[PortProxy|ID])
	 */
	protected void sequence_Trigger(EObject context, ModeTransitionTrigger semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     extended=[ComponentType|QCREF]
	 */
	protected void sequence_TypeExtension(EObject context, TypeExtension semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID baseUnit=[UnitLiteral|ID] factor=NumberValue)
	 */
	protected void sequence_UnitLiteralConversion(EObject context, UnitLiteral semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     name=ID
	 */
	protected void sequence_UnitLiteral(EObject context, UnitLiteral semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID ownedLiteral+=UnitLiteral ownedLiteral+=UnitLiteralConversion*)
	 */
	protected void sequence_UnitsType(EObject context, UnitsType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {AadlBoolean}
	 */
	protected void sequence_UnnamedBooleanType(EObject context, AadlBoolean semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((classifierReference+=QMReference classifierReference+=QMReference*)?)
	 */
	protected void sequence_UnnamedClassifierType(EObject context, ClassifierType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (ownedLiteral+=EnumerationLiteral ownedLiteral+=EnumerationLiteral*)
	 */
	protected void sequence_UnnamedEnumerationType(EObject context, EnumerationType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (range=IntegerRange? (ownedUnitsType=UnnamedUnitsType | referencedUnitsType=[UnitsType|QPREF])?)
	 */
	protected void sequence_UnnamedIntegerType(EObject context, AadlInteger semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (ownedNumberType=UnnamedIntegerType | ownedNumberType=UnnamedRealType | numberType=[NumberType|QPREF])
	 */
	protected void sequence_UnnamedRangeType(EObject context, RangeType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (range=RealRange? (ownedUnitsType=UnnamedUnitsType | referencedUnitsType=[UnitsType|QPREF])?)
	 */
	protected void sequence_UnnamedRealType(EObject context, AadlReal semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ownedField+=RecordField+
	 */
	protected void sequence_UnnamedRecordType(EObject context, RecordType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     ((namedElementReference+=QMReference namedElementReference+=QMReference*)?)
	 */
	protected void sequence_UnnamedReferenceType(EObject context, ReferenceType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     {AadlString}
	 */
	protected void sequence_UnnamedStringType(EObject context, AadlString semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (ownedLiteral+=UnitLiteral ownedLiteral+=UnitLiteralConversion*)
	 */
	protected void sequence_UnnamedUnitsType(EObject context, UnitsType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         ((ownedAbstractSubcomponent+=AbstractSubcomponent | ownedVirtualBusSubcomponent+=VirtualBusSubcomponent)+ | noSubcomponents?='none')? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_VirtualBusImplementation(EObject context, VirtualBusImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_VirtualBusPrototype(EObject context, VirtualBusPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (virtualBusSubcomponentType=[VirtualBusClassifier|QCREF] (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_VirtualBusSubcomponent(EObject context, VirtualBusSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_VirtualBusType(EObject context, VirtualBusType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         ownedRealization=Realization 
	 *         name=INAME 
	 *         ownedExtension=ImplementationExtension? 
	 *         (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)? 
	 *         (ownedPrototype+=Prototype+ | noPrototypes?='none')? 
	 *         (
	 *             (
	 *                 ownedAbstractSubcomponent+=AbstractSubcomponent | 
	 *                 ownedVirtualBusSubcomponent+=VirtualBusSubcomponent | 
	 *                 ownedVirtualProcessorSubcomponent+=VirtualProcessorSubcomponent
	 *             )+ | 
	 *             noSubcomponents?='none'
	 *         )? 
	 *         (ownedEventSource+=EventSource | ownedEventDataSource+=EventDataSource)* 
	 *         (ownedPortProxy+=PortProxy | ownedSubprogramProxy+=SubprogramProxy)* 
	 *         (
	 *             (
	 *                 ownedPortConnection+=PortConnection | 
	 *                 ownedAccessConnection+=AccessConnection | 
	 *                 ownedFeatureGroupConnection+=FeatureGroupConnection | 
	 *                 ownedFeatureConnection+=FeatureConnection
	 *             )+ | 
	 *             noConnections?='none'
	 *         )? 
	 *         ((ownedFlowImplementation+=FlowImplementation | ownedEndToEndFlow+=EndToEndFlow)+ | noFlows?='none')? 
	 *         ((ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_VirtualProcessorImplementation(EObject context, VirtualProcessorImplementation semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (name=ID | refined=[ComponentPrototype|REFINEDNAME])
	 */
	protected void sequence_VirtualProcessorPrototype(EObject context, VirtualProcessorPrototype semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         (name=ID | refined=[Subcomponent|REFINEDNAME]) 
	 *         (
	 *             virtualProcessorSubcomponentType=[VirtualProcessorSubcomponentType|QCREF] 
	 *             (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?
	 *         )? 
	 *         (
	 *             arrayDimension+=ArrayDimension+ 
	 *             (implementationReference+=ComponentImplementationReference implementationReference+=ComponentImplementationReference*)?
	 *         )? 
	 *         ownedPropertyAssociation+=ContainedPropertyAssociation* 
	 *         (ownedModeBinding+=ModeRef ownedModeBinding+=ModeRef*)?
	 *     )
	 */
	protected void sequence_VirtualProcessorSubcomponent(EObject context, VirtualProcessorSubcomponent semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         (ownedExtension=TypeExtension (ownedPrototypeBinding+=PrototypeBinding ownedPrototypeBinding+=PrototypeBinding*)?)? 
	 *         (noPrototypes?='none' | ownedPrototype+=Prototype+)? 
	 *         (
	 *             noFeatures?='none' | 
	 *             (
	 *                 ownedDataPort+=DataPort | 
	 *                 ownedEventPort+=EventPort | 
	 *                 ownedEventDataPort+=EventDataPort | 
	 *                 ownedFeatureGroup+=FeatureGroup | 
	 *                 ownedSubprogramAccess+=SubprogramAccess | 
	 *                 ownedSubprogramGroupAccess+=SubprogramGroupAccess | 
	 *                 ownedAbstractFeature+=AbstractFeature
	 *             )+
	 *         )? 
	 *         (ownedFlowSpecification+=FlowSpecification+ | noFlows?='none')? 
	 *         ((derivedModes?='requires' ownedMode+=Mode+) | (ownedMode+=Mode | ownedModeTransition+=ModeTransition)+ | noModes?='none')? 
	 *         (ownedPropertyAssociation+=ContainedPropertyAssociation+ | noProperties?='none')? 
	 *         ownedAnnexSubclause+=AnnexSubclause*
	 *     )
	 */
	protected void sequence_VirtualProcessorType(EObject context, VirtualProcessorType semanticObject) {
		genericSequencer.createSequence(context, (EObject)semanticObject);
	}
}
