/*
 *
 * <copyright>
 * Copyright  2014 by Carnegie Mellon University, all rights reserved.
 *
 * Use of the Open Source AADL Tool Environment (OSATE) is subject to the terms of the license set forth
 * at http://www.eclipse.org/org/documents/epl-v10.html.
 *
 * NO WARRANTY
 *
 * ANY INFORMATION, MATERIALS, SERVICES, INTELLECTUAL PROPERTY OR OTHER PROPERTY OR RIGHTS GRANTED OR PROVIDED BY
 * CARNEGIE MELLON UNIVERSITY PURSUANT TO THIS LICENSE (HEREINAFTER THE "DELIVERABLES") ARE ON AN "AS-IS" BASIS.
 * CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED AS TO ANY MATTER INCLUDING,
 * BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR A PARTICULAR PURPOSE, MERCHANTABILITY, INFORMATIONAL CONTENT,
 * NONINFRINGEMENT, OR ERROR-FREE OPERATION. CARNEGIE MELLON UNIVERSITY SHALL NOT BE LIABLE FOR INDIRECT, SPECIAL OR
 * CONSEQUENTIAL DAMAGES, SUCH AS LOSS OF PROFITS OR INABILITY TO USE SAID INTELLECTUAL PROPERTY, UNDER THIS LICENSE,
 * REGARDLESS OF WHETHER SUCH PARTY WAS AWARE OF THE POSSIBILITY OF SUCH DAMAGES. LICENSEE AGREES THAT IT WILL NOT
 * MAKE ANY WARRANTY ON BEHALF OF CARNEGIE MELLON UNIVERSITY, EXPRESS OR IMPLIED, TO ANY PERSON CONCERNING THE
 * APPLICATION OF OR THE RESULTS TO BE OBTAINED WITH THE DELIVERABLES UNDER THIS LICENSE.
 *
 * Licensee hereby agrees to defend, indemnify, and hold harmless Carnegie Mellon University, its trustees, officers,
 * employees, and agents from all claims or demands made against them (and any related losses, expenses, or
 * attorney's fees) arising out of, or relating to Licensee's and/or its sub licensees' negligent use or willful
 * misuse of or negligent conduct or willful misconduct regarding the Software, facilities, or other rights or
 * assistance granted by Carnegie Mellon University under this License, including, but not limited to, any claims of
 * product liability, personal injury, death, damage to property, or violation of any laws or regulations.
 *
 * Carnegie Mellon Carnegie Mellon University Software Engineering Institute authored documents are sponsored by the U.S. Department
 * of Defense under Contract F19628-00-C-0003. Carnegie Mellon University retains copyrights in all material produced
 * under this contract. The U.S. Government retains a non-exclusive, royalty-free license to publish or reproduce these
 * documents, or allow others to do so, for U.S. Government purposes only pursuant to the copyright license
 * under the contract clause at 252.227.7013.
 * </copyright>
 */
package org.osate.xtext.aadl2.tests

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.eclipse.xtext.scoping.IScopeProvider
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.osate.aadl2.Aadl2Package
import org.osate.aadl2.AadlPackage
import org.osate.aadl2.ComponentPrototypeBinding
import org.osate.aadl2.ModelUnit
import org.osate.xtext.aadl2.Aadl2UiInjectorProvider
import org.junit.After
import org.osate.aadl2.FeatureGroupPrototypeBinding
import org.osate.aadl2.NamedElement
import org.osate.aadl2.ComponentImplementation
import org.osate.aadl2.FeaturePrototypeBinding

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(Aadl2UiInjectorProvider))
class Aadl2ScopeProviderTests extends OsateTest {
	@Inject extension ParseHelper<ModelUnit>
	@Inject extension ValidationTestHelper
	@Inject extension IScopeProvider
	
	static val TEST_PROJECT_NAME = "Aadl2_Scope_Provider_Test"
	
	@Before
	def setUp() {
		createProject(TEST_PROJECT_NAME, "packages")
		buildProject("Plugin_Resources", true)
		setResourceRoot("platform:/resource/Aadl2_Scope_Provider_Test/packages")
	}
	
	@After
	def cleanUp() {
		deleteProject(TEST_PROJECT_NAME)
	}
	
	/*
	 * Tests scope_ComponentPrototype_constrainingClassifier, scope_FeaturePrototype_constrainingClassifier, scope_FeatureGroupPrototypeActual_featureType,
	 * scope_PortSpecification_classifier, scope_AccessSpecification_classifier, and scope_ComponentPrototypeActual_subcomponentType
	 */
	@Test
	def void testRenamesInClassifierReferenceScope() {
		createFiles(
			"pack1.aadl" -> '''
				package pack1
				public
				  with pack3;
				  with pack4;
				  with pack5;
				  renames pack3::all;
				  renamed_package renames package pack4;
				  renames abstract pack5::a6;
				  renames feature group pack5::fgt5;
				  renmaed_classifier renames abstract pack5::a7;
				  renamed_feature_group renames feature group pack5::fgt5;
				
				  abstract a1
				    prototypes
				      proto1: abstract a2;
				      proto2: feature a2;
				      proto3: feature group;
				      proto4: feature;
				      proto5: feature;
				      proto6: data;
				  end a1;
				  
				  abstract a2 extends a1 (
				    proto3 => feature group fgt1,
				    proto4 => in data port d1,
				    proto5 => provides data access d1,
				    proto6 => data d1
				  )
				  end a2;
				  
				  abstract implementation a2.i
				  end a2.i;
				  
				  feature group fgt1
				  end fgt1;
				  
				  data d1
				  end d1;
				  
				  data implementation d1.i
				  end d1.i;
				end pack1;
			''',
			"pack2.aadl" -> '''
				package pack2
				public
				  abstract a3
				  end a3;
				  
				  abstract implementation a3.i
				  end a3.i;
				  
				  feature group fgt2
				  end fgt2;
				  
				  data d2
				  end d2;
				  
				  data implementation d2.i
				  end d2.i;
			''',
			"pack3.aadl" -> '''
				package pack3
				public
				  abstract a4
				  end a4;
				  
				  abstract implementation a4.i
				  end a4.i;
				  
				  feature group fgt3
				  end fgt3;
				  
				  data d3
				  end d3;
				  
				  data implementation d3.i
				  end d3.i;
				end pack3;
			''',
			"pack4.aadl" -> '''
				package pack4
				public
				  abstract a5
				  end a5;
				  
				  abstract implementation a5.i
				  end a5.i;
				  
				  feature group fgt4
				  end fgt4;
				  
				  data d4
				  end d4;
				  
				  data implementation d4.i
				  end d4.i;
				end pack4;
			''',
			"pack5.aadl" -> '''
				package pack5
				public
				  abstract a6
				  end a6;
				  
				  abstract a7
				  end a7;
				  
				  feature group fgt5
				  end fgt5;
				  
				  data d5
				  end d5;
				  
				  data implementation d5.i
				  end d5.i;
				end pack5;
			'''
		)
		suppressSerialization
		val pack1 = testFile("pack1.aadl").resource.contents.head as AadlPackage
		assertAllCrossReferencesResolvable(pack1)
		
		val componentClassifierScopeForPack1 = '''
			a6, renmaed_classifier, a4, a4.i, d3, d3.i, renamed_package.a5, renamed_package.a5.i, renamed_package.d4, renamed_package.d4.i, 
			a1, a2, a2.i, d1, d1.i, pack1.a1, pack1.a2, pack1.a2.i, pack1.d1, pack1.d1.i, pack3.a4, pack3.a4.i, pack3.d3, pack3.d3.i, pack2.a3, 
			pack2.a3.i, pack2.d2, pack2.d2.i, pack5.a6, pack5.a7, pack5.d5, pack5.d5.i, pack4.a5, pack4.a5.i, pack4.d4, pack4.d4.i, 
			Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, Base_Types.Integer_64, 
			Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, Base_Types.Natural, Base_Types.Float, 
			Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String
		'''.toString.replaceAll(System.lineSeparator, "")
		
		pack1 => [
			Assert::assertEquals("pack1", name)
			publicSection.ownedClassifiers.get(0) => [
				Assert::assertEquals("a1", name)
				ownedPrototypes.get(0) => [
					Assert::assertEquals("proto1", name)
					//Tests scope_ComponentPrototype_constrainingClassifier
					assertScope(Aadl2Package::eINSTANCE.componentPrototype_ConstrainingClassifier, componentClassifierScopeForPack1)
				]
				ownedPrototypes.get(1) => [
					Assert::assertEquals("proto2", name)
					//Tests scope_FeaturePrototype_constrainingClassifier
					assertScope(Aadl2Package::eINSTANCE.featurePrototype_ConstrainingClassifier, componentClassifierScopeForPack1)
				]
			]
			publicSection.ownedClassifiers.get(1) => [
				Assert::assertEquals("a2", name)
				ownedPrototypeBindings.get(0) as FeatureGroupPrototypeBinding => [
					Assert::assertEquals("proto3", formal.name)
					//Tests scope_FeatureGroupPrototypeActual_featureType
					actual.assertScope(Aadl2Package::eINSTANCE.featureGroupPrototypeActual_FeatureType, "proto3, fgt5, renamed_feature_group, " +
						"fgt3, renamed_package.fgt4, fgt1, pack1.fgt1, pack3.fgt3, pack2.fgt2, pack5.fgt5, pack4.fgt4"
					)
				]
				ownedPrototypeBindings.get(1) as FeaturePrototypeBinding => [
					Assert::assertEquals("proto4", formal.name)
					//Tests scope_PortSpecification_classifier
					actual.assertScope(Aadl2Package::eINSTANCE.portSpecification_Classifier, componentClassifierScopeForPack1)
				]
				ownedPrototypeBindings.get(2) as FeaturePrototypeBinding => [
					Assert::assertEquals("proto5", formal.name)
					//Tests scope_AccessSpecification_classifier
					actual.assertScope(Aadl2Package::eINSTANCE.accessSpecification_Classifier, componentClassifierScopeForPack1)
				]
				ownedPrototypeBindings.get(3) as ComponentPrototypeBinding => [
					Assert::assertEquals("proto6", formal.name)
					//Tests scope_ComponentPrototypeActual_subcomponentType
					actuals.get(0).assertScope(Aadl2Package::eINSTANCE.componentPrototypeActual_SubcomponentType, "proto1, proto6, " + componentClassifierScopeForPack1)
				]
			]
		]
	}
	
	//Tests scope_Prototype_refined and scope_Subcomponent_refined
	@Test
	def void testRefinedElements() {
		('''
			package pack
			public
			  abstract a1
			  prototypes
			    proto1: abstract;
			  end a1;
			  
			  abstract a2 extends a1
			  prototypes
			    proto1: refined to abstract;
			  end a2;
			  
			  feature group fgt1
			  prototypes
			    proto2: abstract;
			  end fgt1;
			  
			  feature group fgt2 extends fgt1
			  prototypes
			    proto2: refined to abstract;
			  end fgt2;
			  
			  abstract implementation a1.i1
			  subcomponents
			    asub1: abstract;
			  end a1.i1;
			  
			  abstract implementation a1.i2 extends a1.i1
			  subcomponents
			    asub1: refined to abstract;
			    asub2: abstract;
			  end a1.i2;
			end pack;
		'''.parse as AadlPackage) => [
			Assert::assertEquals("pack", name)
			publicSection.ownedClassifiers.get(0) => [
				Assert::assertEquals("a1", name)
				ownedPrototypes.get(0) => [
					Assert::assertEquals("proto1", name)
					//Tests scope_Prototype_refined
					assertScope(Aadl2Package::eINSTANCE.prototype_Refined, "")
				]
			]
			publicSection.ownedClassifiers.get(1) => [
				Assert::assertEquals("a2", name)
				ownedPrototypes.get(0) => [
					Assert::assertEquals("proto1", name)
					//Tests scope_Prototype_refined
					assertScope(Aadl2Package::eINSTANCE.prototype_Refined, "proto1")
				]
			]
			publicSection.ownedClassifiers.get(2) => [
				Assert::assertEquals("fgt1", name)
				ownedPrototypes.get(0) => [
					Assert::assertEquals("proto2", name)
					//Tests scope_Prototype_refined
					assertScope(Aadl2Package::eINSTANCE.prototype_Refined, "")
				]
			]
			publicSection.ownedClassifiers.get(3) => [
				Assert::assertEquals("fgt2", name)
				ownedPrototypes.get(0) => [
					Assert::assertEquals("proto2", name)
					//Tests scope_Prototype_refined
					assertScope(Aadl2Package::eINSTANCE.prototype_Refined, "proto2")
				]
			]
			publicSection.ownedClassifiers.get(5) as ComponentImplementation => [
				Assert::assertEquals("a1.i2", name)
				ownedSubcomponents.get(0) => [
					Assert::assertEquals("asub1", name)
					//Tests scope_Subcomponent_refined
					assertScope(Aadl2Package::eINSTANCE.subcomponent_Refined, "asub1")
				]
			]
		]
	}
	
	/*
	 * Tests scope_PrototypeBinding_formal, scope_FeatureGroupPrototypeActual_featureType, scope_FeaturePrototypeReference_prototype, and
	 * scope_ComponentPrototypeActual_subcomponentType
	 */
	@Test
	def void testPrototypeBindings() {
		('''
			package pack
			public
			  abstract a1
			  prototypes
			    proto1: abstract;
			    proto3: feature group;
			    proto5: feature group;
			    proto8: feature;
			    proto9: feature;
			    proto11: abstract;
			  end a1;
			  
			  abstract a2 extends a1 (
			    proto1 => abstract a3 (
			      proto2 => in data port
			    ),
			    proto3 => feature group fgt1 (
			      proto4 => in data port
			    )
			  )
			  end a2;
			  
			  abstract implementation a1.i1
			  subcomponents
			    asub1: abstract [](a3.i1 (
			      proto2 => in data port
			    ));
			    asub2: abstract a3 (
			      proto2 => in data port
			    );
			  end a1.i1;
			  
			  abstract implementation a1.i2 extends a1.i1 (
			    proto1 => abstract a3,
			    proto3 => feature group fgt1,
			    proto5 => feature group proto3,
			    proto8 => feature proto9,
			    proto11 => abstract proto1
			  )
			  end a1.i2;
			  
			  abstract a3
			  prototypes
			    proto2: feature;
			  end a3;
			  
			  abstract implementation a3.i1
			  end a3.i1;
			  
			  feature group fgt1
			  prototypes
			    proto4: feature;
			    proto6: feature group;
			    proto10: feature;
			    proto12: abstract;
			  end fgt1;
			  
			  feature group fgt2 extends fgt1 (
			    proto4 => in data port,
			    proto6 => feature group proto7,
			    proto10 => feature proto4,
			    proto12 => abstract proto13
			  )
			  prototypes
			    proto7: feature group;
			    proto13: abstract;
			  end fgt2;
			end pack;
		'''.parse as AadlPackage) => [
			Assert::assertEquals("pack", name)
			publicSection.ownedClassifiers.get(1) => [
				Assert::assertEquals("a2", name)
				ownedPrototypeBindings.get(0) as ComponentPrototypeBinding => [
					Assert::assertEquals("proto1", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11")
					actuals.get(0) => [
						Assert::assertEquals("a3", subcomponentType.name)
						bindings.get(0) => [
							Assert::assertEquals("proto2", formal.name)
							//Tests scope_PrototypeBinding_formal(ComponentPrototypeActual, EReference)
							assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto2")
						]
					]
				]
				ownedPrototypeBindings.get(1) as FeatureGroupPrototypeBinding => [
					Assert::assertEquals("proto3", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11")
					actual => [
						Assert::assertEquals("fgt1", (featureType as NamedElement).name)
						//Tests scope_FeatureGroupPrototypeActual_featureType
						assertScope(Aadl2Package::eINSTANCE.featureGroupPrototypeActual_FeatureType, "proto3, proto5, fgt1, fgt2, pack.fgt1, pack.fgt2")
						bindings.get(0) => [
							Assert::assertEquals("proto4", formal.name)
							//Tests scope_PrototypeBinding_formal(FeatureGroupPrototypeActual, EReference)
							assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto4, proto6, proto10, proto12")
						]
					]
				]
			]
			publicSection.ownedClassifiers.get(2) as ComponentImplementation => [
				Assert::assertEquals("a1.i1", name)
				ownedSubcomponents.get(0) => [
					Assert::assertEquals("asub1", name)
					implementationReferences.get(0) => [
						Assert::assertEquals("a3.i1", implementation.name)
						ownedPrototypeBindings.get(0) => [
							Assert::assertEquals("proto2", formal.name)
							//Tests scope_PrototypeBinding_formal(ComponentImplementationReference, EReference)
							assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto2")
						]
					]
				]
				ownedSubcomponents.get(1) => [
					Assert::assertEquals("asub2", name)
					ownedPrototypeBindings.get(0) => [
						Assert::assertEquals("proto2", formal.name)
						//Tests scope_PrototypeBinding_formal(Subcomponent, EReference)
						assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto2")
					]
				]
			]
			publicSection.ownedClassifiers.get(3) => [
				Assert::assertEquals("a1.i2", name)
				ownedPrototypeBindings.get(0) as ComponentPrototypeBinding => [
					Assert::assertEquals("proto1", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11")
					//Tests scope_ComponentPrototypeActual_subcomponentType
					actuals.get(0).assertScope(Aadl2Package::eINSTANCE.componentPrototypeActual_SubcomponentType, "proto1, proto11, a1, a2, a1.i1, a1.i2, a3, " +
						"a3.i1, pack.a1, pack.a2, pack.a1.i1, pack.a1.i2, pack.a3, pack.a3.i1"
					)
				]
				ownedPrototypeBindings.get(1) as FeatureGroupPrototypeBinding => [
					Assert::assertEquals("proto3", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11")
					//Tests scope_FeatureGroupPrototypeActual_featureType
					actual.assertScope(Aadl2Package::eINSTANCE.featureGroupPrototypeActual_FeatureType, "proto3, proto5, fgt1, fgt2, pack.fgt1, pack.fgt2")
				]
				ownedPrototypeBindings.get(2) as FeatureGroupPrototypeBinding => [
					Assert::assertEquals("proto5", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11")
					//Tests scope_FeatureGroupPrototypeActual_featureType
					actual.assertScope(Aadl2Package::eINSTANCE.featureGroupPrototypeActual_FeatureType, "proto3, proto5, fgt1, fgt2, pack.fgt1, pack.fgt2")
				]
				ownedPrototypeBindings.get(3) as FeaturePrototypeBinding => [
					Assert::assertEquals("proto8", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11")
					//Tests scope_FeaturePrototypeReference_prototype
					actual.assertScope(Aadl2Package::eINSTANCE.featurePrototypeReference_Prototype, "proto8, proto9")
				]
				ownedPrototypeBindings.get(4) as ComponentPrototypeBinding => [
					Assert::assertEquals("proto11", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11")
					//Tests scope_ComponentPrototypeActual_subcomponentType
					actuals.get(0).assertScope(Aadl2Package::eINSTANCE.componentPrototypeActual_SubcomponentType, "proto1, proto11, a1, a2, a1.i1, a1.i2, a3, " +
						"a3.i1, pack.a1, pack.a2, pack.a1.i1, pack.a1.i2, pack.a3, pack.a3.i1"
					)
				]
			]
			publicSection.ownedClassifiers.get(7) => [
				Assert::assertEquals("fgt2", name)
				ownedPrototypeBindings.get(0) => [
					Assert::assertEquals("proto4", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto4, proto6, proto10, proto12")
				]
				ownedPrototypeBindings.get(1) as FeatureGroupPrototypeBinding => [
					Assert::assertEquals("proto6", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto4, proto6, proto10, proto12")
					//Tests scope_FeatureGroupPrototypeActual_featureType
					actual.assertScope(Aadl2Package::eINSTANCE.featureGroupPrototypeActual_FeatureType, "proto6, proto7, fgt1, fgt2, pack.fgt1, pack.fgt2")
				]
				ownedPrototypeBindings.get(2) as FeaturePrototypeBinding => [
					Assert::assertEquals("proto10", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto4, proto6, proto10, proto12")
					//Tests scope_FeaturePrototypeReference_prototype
					actual.assertScope(Aadl2Package::eINSTANCE.featurePrototypeReference_Prototype, "proto4, proto10")
				]
				ownedPrototypeBindings.get(3) as ComponentPrototypeBinding => [
					Assert::assertEquals("proto12", formal.name)
					//Tests scope_PrototypeBinding_formal(Classifier, EReference)
					assertScope(Aadl2Package::eINSTANCE.prototypeBinding_Formal, "proto4, proto6, proto10, proto12")
					//Tests scope_ComponentPrototypeActual_subcomponentType
					actuals.get(0).assertScope(Aadl2Package::eINSTANCE.componentPrototypeActual_SubcomponentType, "proto12, proto13, a1, a2, a1.i1, a1.i2, a3, " +
						"a3.i1, pack.a1, pack.a2, pack.a1.i1, pack.a1.i2, pack.a3, pack.a3.i1"
					)
				]
			]
		]
	}
	
	def private assertScope(EObject context, EReference reference, CharSequence expected) {
		context.assertNoErrors
		Assert.assertEquals(expected, context.getScope(reference).allElements.map[name].join(", "))
	}
}