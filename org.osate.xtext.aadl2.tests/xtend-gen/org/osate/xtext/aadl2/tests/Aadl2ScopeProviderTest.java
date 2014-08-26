/**
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
package org.osate.xtext.aadl2.tests;

import com.google.common.collect.Sets;
import com.google.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipselabs.xtext.utils.unittesting.FluentIssueCollection;
import org.eclipselabs.xtext.utils.unittesting.XtextRunner2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.osate.aadl2.Aadl2Package;
import org.osate.aadl2.AadlPackage;
import org.osate.aadl2.AbstractFeature;
import org.osate.aadl2.AbstractImplementation;
import org.osate.aadl2.AbstractSubcomponent;
import org.osate.aadl2.AbstractType;
import org.osate.aadl2.ArrayDimension;
import org.osate.aadl2.ArraySize;
import org.osate.aadl2.ArraySizeProperty;
import org.osate.aadl2.BusAccess;
import org.osate.aadl2.BusSubcomponent;
import org.osate.aadl2.Classifier;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentImplementationReference;
import org.osate.aadl2.ComponentPrototypeActual;
import org.osate.aadl2.ComponentPrototypeBinding;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.DataAccess;
import org.osate.aadl2.DataPort;
import org.osate.aadl2.DataSubcomponent;
import org.osate.aadl2.DeviceSubcomponent;
import org.osate.aadl2.EventDataPort;
import org.osate.aadl2.EventPort;
import org.osate.aadl2.FeatureGroup;
import org.osate.aadl2.FeatureGroupPrototypeActual;
import org.osate.aadl2.FeatureGroupPrototypeBinding;
import org.osate.aadl2.FeatureGroupType;
import org.osate.aadl2.FeaturePrototypeActual;
import org.osate.aadl2.FeaturePrototypeBinding;
import org.osate.aadl2.FeatureType;
import org.osate.aadl2.MemorySubcomponent;
import org.osate.aadl2.ModelUnit;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.Parameter;
import org.osate.aadl2.ProcessSubcomponent;
import org.osate.aadl2.ProcessorSubcomponent;
import org.osate.aadl2.Prototype;
import org.osate.aadl2.PrototypeBinding;
import org.osate.aadl2.PublicPackageSection;
import org.osate.aadl2.Subcomponent;
import org.osate.aadl2.SubcomponentType;
import org.osate.aadl2.SubprogramAccess;
import org.osate.aadl2.SubprogramGroupAccess;
import org.osate.aadl2.SubprogramGroupSubcomponent;
import org.osate.aadl2.SubprogramSubcomponent;
import org.osate.aadl2.SubprogramType;
import org.osate.aadl2.SystemSubcomponent;
import org.osate.aadl2.ThreadGroupSubcomponent;
import org.osate.aadl2.ThreadSubcomponent;
import org.osate.aadl2.VirtualBusSubcomponent;
import org.osate.aadl2.VirtualProcessorSubcomponent;
import org.osate.aadl2.modelsupport.util.AadlUtil;
import org.osate.xtext.aadl2.Aadl2UiInjectorProvider;
import org.osate.xtext.aadl2.tests.OsateTest;

@RunWith(XtextRunner2.class)
@InjectWith(Aadl2UiInjectorProvider.class)
@SuppressWarnings("all")
public class Aadl2ScopeProviderTest extends OsateTest {
  @Inject
  @Extension
  private ParseHelper<ModelUnit> _parseHelper;
  
  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;
  
  @Inject
  @Extension
  private IScopeProvider _iScopeProvider;
  
  private final static String TEST_PROJECT_NAME = "Aadl2_Scope_Provider_Test";
  
  @Before
  public void setUp() {
    this.createProject(Aadl2ScopeProviderTest.TEST_PROJECT_NAME, "packages");
    this.buildProject("Plugin_Resources", true);
    this.setResourceRoot("platform:/resource/Aadl2_Scope_Provider_Test/packages");
  }
  
  @After
  public void cleanUp() {
    this.deleteProject(Aadl2ScopeProviderTest.TEST_PROJECT_NAME);
  }
  
  /**
   * Tests scope_ComponentPrototype_constrainingClassifier, scope_FeaturePrototype_constrainingClassifier, scope_FeatureGroupPrototypeActual_featureType,
   * scope_PortSpecification_classifier, scope_AccessSpecification_classifier, and scope_ComponentPrototypeActual_subcomponentType
   */
  @Test
  public void testRenamesInClassifierReferenceScope() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package pack1");
    _builder.newLine();
    _builder.append("public");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("with pack3;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("with pack4;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("with pack5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames pack3::all;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renamed_package renames package pack4;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames abstract pack5::a6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames feature group pack5::fgt5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renmaed_classifier renames abstract pack5::a7;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renamed_feature_group renames feature group pack5::fgt5;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("  ");
    _builder.append("abstract a1");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("prototypes");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("proto1: abstract a2;");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("proto2: feature a2;");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("proto3: feature group;");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("proto4: feature;");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("proto5: feature;");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("proto6: data;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end a1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("abstract a2 extends a1 (");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("proto3 => feature group fgt1,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("proto4 => in data port d1,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("proto5 => provides data access d1,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("proto6 => data d1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append(")");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end a2;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("abstract implementation a2.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end a2.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("feature group fgt1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end fgt1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("data d1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end d1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("data implementation d1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end d1.i;");
    _builder.newLine();
    _builder.append("end pack1;");
    _builder.newLine();
    Pair<String, String> _mappedTo = Pair.<String, String>of("pack1.aadl", _builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("package pack2");
    _builder_1.newLine();
    _builder_1.append("public");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("abstract a3");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end a3;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("abstract implementation a3.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end a3.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("feature group fgt2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end fgt2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("data d2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end d2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("data implementation d2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end d2.i;");
    _builder_1.newLine();
    Pair<String, String> _mappedTo_1 = Pair.<String, String>of("pack2.aadl", _builder_1.toString());
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("package pack3");
    _builder_2.newLine();
    _builder_2.append("public");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("abstract a4");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end a4;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("abstract implementation a4.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end a4.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("feature group fgt3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end fgt3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("data d3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end d3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("data implementation d3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end d3.i;");
    _builder_2.newLine();
    _builder_2.append("end pack3;");
    _builder_2.newLine();
    Pair<String, String> _mappedTo_2 = Pair.<String, String>of("pack3.aadl", _builder_2.toString());
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("package pack4");
    _builder_3.newLine();
    _builder_3.append("public");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("abstract a5");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end a5;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("abstract implementation a5.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end a5.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("feature group fgt4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end fgt4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("data d4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end d4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("data implementation d4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end d4.i;");
    _builder_3.newLine();
    _builder_3.append("end pack4;");
    _builder_3.newLine();
    Pair<String, String> _mappedTo_3 = Pair.<String, String>of("pack4.aadl", _builder_3.toString());
    StringConcatenation _builder_4 = new StringConcatenation();
    _builder_4.append("package pack5");
    _builder_4.newLine();
    _builder_4.append("public");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("abstract a6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end a6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("abstract a7");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end a7;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("feature group fgt5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end fgt5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("data d5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end d5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("data implementation d5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end d5.i;");
    _builder_4.newLine();
    _builder_4.append("end pack5;");
    _builder_4.newLine();
    Pair<String, String> _mappedTo_4 = Pair.<String, String>of("pack5.aadl", _builder_4.toString());
    this.createFiles(_mappedTo, _mappedTo_1, _mappedTo_2, _mappedTo_3, _mappedTo_4);
    this.suppressSerialization();
    FluentIssueCollection _testFile = this.testFile("pack1.aadl");
    Resource _resource = _testFile.getResource();
    EList<EObject> _contents = _resource.getContents();
    EObject _head = IterableExtensions.<EObject>head(_contents);
    final AadlPackage pack1 = ((AadlPackage) _head);
    this.assertAllCrossReferencesResolvable(pack1);
    StringConcatenation _builder_5 = new StringConcatenation();
    _builder_5.append("a6, renmaed_classifier, a4, a4.i, d3, d3.i, renamed_package.a5, renamed_package.a5.i, renamed_package.d4, renamed_package.d4.i, ");
    _builder_5.newLine();
    _builder_5.append("a1, a2, a2.i, d1, d1.i, pack1.a1, pack1.a2, pack1.a2.i, pack1.d1, pack1.d1.i, pack3.a4, pack3.a4.i, pack3.d3, pack3.d3.i, pack2.a3, ");
    _builder_5.newLine();
    _builder_5.append("pack2.a3.i, pack2.d2, pack2.d2.i, pack5.a6, pack5.a7, pack5.d5, pack5.d5.i, pack4.a5, pack4.a5.i, pack4.d4, pack4.d4.i, ");
    _builder_5.newLine();
    _builder_5.append("Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, Base_Types.Integer_64, ");
    _builder_5.newLine();
    _builder_5.append("Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, Base_Types.Natural, Base_Types.Float, ");
    _builder_5.newLine();
    _builder_5.append("Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String");
    _builder_5.newLine();
    String _string = _builder_5.toString();
    String _lineSeparator = System.lineSeparator();
    final String componentClassifierScopeForPack1 = _string.replaceAll(_lineSeparator, "");
    final Procedure1<AadlPackage> _function = new Procedure1<AadlPackage>() {
      public void apply(final AadlPackage it) {
        String _name = it.getName();
        Assert.assertEquals("pack1", _name);
        PublicPackageSection _publicSection = it.getPublicSection();
        EList<Classifier> _ownedClassifiers = _publicSection.getOwnedClassifiers();
        Classifier _get = _ownedClassifiers.get(0);
        final Procedure1<Classifier> _function = new Procedure1<Classifier>() {
          public void apply(final Classifier it) {
            String _name = it.getName();
            Assert.assertEquals("a1", _name);
            EList<Prototype> _ownedPrototypes = it.getOwnedPrototypes();
            Prototype _get = _ownedPrototypes.get(0);
            final Procedure1<Prototype> _function = new Procedure1<Prototype>() {
              public void apply(final Prototype it) {
                String _name = it.getName();
                Assert.assertEquals("proto1", _name);
                EReference _componentPrototype_ConstrainingClassifier = Aadl2Package.eINSTANCE.getComponentPrototype_ConstrainingClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _componentPrototype_ConstrainingClassifier, componentClassifierScopeForPack1);
              }
            };
            ObjectExtensions.<Prototype>operator_doubleArrow(_get, _function);
            EList<Prototype> _ownedPrototypes_1 = it.getOwnedPrototypes();
            Prototype _get_1 = _ownedPrototypes_1.get(1);
            final Procedure1<Prototype> _function_1 = new Procedure1<Prototype>() {
              public void apply(final Prototype it) {
                String _name = it.getName();
                Assert.assertEquals("proto2", _name);
                EReference _featurePrototype_ConstrainingClassifier = Aadl2Package.eINSTANCE.getFeaturePrototype_ConstrainingClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _featurePrototype_ConstrainingClassifier, componentClassifierScopeForPack1);
              }
            };
            ObjectExtensions.<Prototype>operator_doubleArrow(_get_1, _function_1);
          }
        };
        ObjectExtensions.<Classifier>operator_doubleArrow(_get, _function);
        PublicPackageSection _publicSection_1 = it.getPublicSection();
        EList<Classifier> _ownedClassifiers_1 = _publicSection_1.getOwnedClassifiers();
        Classifier _get_1 = _ownedClassifiers_1.get(1);
        final Procedure1<Classifier> _function_1 = new Procedure1<Classifier>() {
          public void apply(final Classifier it) {
            String _name = it.getName();
            Assert.assertEquals("a2", _name);
            EList<PrototypeBinding> _ownedPrototypeBindings = it.getOwnedPrototypeBindings();
            PrototypeBinding _get = _ownedPrototypeBindings.get(0);
            final Procedure1<FeatureGroupPrototypeBinding> _function = new Procedure1<FeatureGroupPrototypeBinding>() {
              public void apply(final FeatureGroupPrototypeBinding it) {
                Prototype _formal = it.getFormal();
                String _name = _formal.getName();
                Assert.assertEquals("proto3", _name);
                FeatureGroupPrototypeActual _actual = it.getActual();
                EReference _featureGroupPrototypeActual_FeatureType = Aadl2Package.eINSTANCE.getFeatureGroupPrototypeActual_FeatureType();
                Aadl2ScopeProviderTest.this.assertScope(_actual, _featureGroupPrototypeActual_FeatureType, ("proto3, fgt5, renamed_feature_group, " + 
                  "fgt3, renamed_package.fgt4, fgt1, pack1.fgt1, pack3.fgt3, pack2.fgt2, pack5.fgt5, pack4.fgt4"));
              }
            };
            ObjectExtensions.<FeatureGroupPrototypeBinding>operator_doubleArrow(
              ((FeatureGroupPrototypeBinding) _get), _function);
            EList<PrototypeBinding> _ownedPrototypeBindings_1 = it.getOwnedPrototypeBindings();
            PrototypeBinding _get_1 = _ownedPrototypeBindings_1.get(1);
            final Procedure1<FeaturePrototypeBinding> _function_1 = new Procedure1<FeaturePrototypeBinding>() {
              public void apply(final FeaturePrototypeBinding it) {
                Prototype _formal = it.getFormal();
                String _name = _formal.getName();
                Assert.assertEquals("proto4", _name);
                FeaturePrototypeActual _actual = it.getActual();
                EReference _portSpecification_Classifier = Aadl2Package.eINSTANCE.getPortSpecification_Classifier();
                Aadl2ScopeProviderTest.this.assertScope(_actual, _portSpecification_Classifier, componentClassifierScopeForPack1);
              }
            };
            ObjectExtensions.<FeaturePrototypeBinding>operator_doubleArrow(
              ((FeaturePrototypeBinding) _get_1), _function_1);
            EList<PrototypeBinding> _ownedPrototypeBindings_2 = it.getOwnedPrototypeBindings();
            PrototypeBinding _get_2 = _ownedPrototypeBindings_2.get(2);
            final Procedure1<FeaturePrototypeBinding> _function_2 = new Procedure1<FeaturePrototypeBinding>() {
              public void apply(final FeaturePrototypeBinding it) {
                Prototype _formal = it.getFormal();
                String _name = _formal.getName();
                Assert.assertEquals("proto5", _name);
                FeaturePrototypeActual _actual = it.getActual();
                EReference _accessSpecification_Classifier = Aadl2Package.eINSTANCE.getAccessSpecification_Classifier();
                Aadl2ScopeProviderTest.this.assertScope(_actual, _accessSpecification_Classifier, componentClassifierScopeForPack1);
              }
            };
            ObjectExtensions.<FeaturePrototypeBinding>operator_doubleArrow(
              ((FeaturePrototypeBinding) _get_2), _function_2);
            EList<PrototypeBinding> _ownedPrototypeBindings_3 = it.getOwnedPrototypeBindings();
            PrototypeBinding _get_3 = _ownedPrototypeBindings_3.get(3);
            final Procedure1<ComponentPrototypeBinding> _function_3 = new Procedure1<ComponentPrototypeBinding>() {
              public void apply(final ComponentPrototypeBinding it) {
                Prototype _formal = it.getFormal();
                String _name = _formal.getName();
                Assert.assertEquals("proto6", _name);
                EList<ComponentPrototypeActual> _actuals = it.getActuals();
                ComponentPrototypeActual _get = _actuals.get(0);
                EReference _componentPrototypeActual_SubcomponentType = Aadl2Package.eINSTANCE.getComponentPrototypeActual_SubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(_get, _componentPrototypeActual_SubcomponentType, ("proto1, proto6, " + componentClassifierScopeForPack1));
              }
            };
            ObjectExtensions.<ComponentPrototypeBinding>operator_doubleArrow(
              ((ComponentPrototypeBinding) _get_3), _function_3);
          }
        };
        ObjectExtensions.<Classifier>operator_doubleArrow(_get_1, _function_1);
      }
    };
    ObjectExtensions.<AadlPackage>operator_doubleArrow(pack1, _function);
  }
  
  @Test
  public void testRefinedElements() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package pack");
      _builder.newLine();
      _builder.append("public");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("abstract a1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("prototypes");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto1: abstract;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("features");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("dport1: in data port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("edport1: in event data port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("eport1: in event port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("fg1: feature group;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("suba1: provides subprogram access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("subga1: provides subprogram group access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("ba1: provides bus access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("da1: provides data access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("af1: feature;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end a1;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("abstract a2 extends a1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("prototypes");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto1: refined to abstract;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("features");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("dport1: refined to in data port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("edport1: refined to in event data port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("eport1: refined to in event port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("fg1: refined to feature group;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("suba1: refined to provides subprogram access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("subga1: refined to provides subprogram group access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("ba1: refined to provides bus access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("da1: refined to provides data access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("af1: refined to feature;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end a2;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("feature group fgt1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("prototypes");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto2: abstract;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("features");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("dport2: in data port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("edport2: in event data port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("eport2: in event port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("fg2: feature group;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("param1: in parameter;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("suba2: provides subprogram access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("subga2: provides subprogram group access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("ba2: provides bus access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("da2: provides data access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("af2: feature;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end fgt1;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("feature group fgt2 extends fgt1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("prototypes");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto2: refined to abstract;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("features");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("dport2: refined to in data port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("edport2: refined to in event data port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("eport2: refined to in event port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("fg2: refined to feature group;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("param1: refined to in parameter;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("suba2: refined to provides subprogram access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("subga2: refined to provides subprogram group access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("ba2: refined to provides bus access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("da2: refined to provides data access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("af2: refined to feature;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end fgt2;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("abstract implementation a1.i1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("subcomponents");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("asub1: abstract;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end a1.i1;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("abstract implementation a1.i2 extends a1.i1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("subcomponents");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("asub1: refined to abstract;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("asub2: abstract;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end a1.i2;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("subprogram sub1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("features");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("edport3: in event data port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("eport3: in event port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("fg3: feature group;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("param2: in parameter;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("suba3: requires subprogram access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("subga3: requires subprogram group access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("da3: requires data access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("af3: feature;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end sub1;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("subprogram sub2 extends sub1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("features");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("edport3: refined to in event data port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("eport3: refined to in event port;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("fg3: refined to feature group;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("param2: refined to in parameter;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("suba3: refined to requires subprogram access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("subga3: refined to requires subprogram group access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("da3: refined to requires data access;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("af3: refined to feature;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end sub2;");
      _builder.newLine();
      _builder.append("end pack;");
      _builder.newLine();
      ModelUnit _parse = this._parseHelper.parse(_builder);
      final Procedure1<AadlPackage> _function = new Procedure1<AadlPackage>() {
        public void apply(final AadlPackage it) {
          String _name = it.getName();
          Assert.assertEquals("pack", _name);
          PublicPackageSection _publicSection = it.getPublicSection();
          EList<Classifier> _ownedClassifiers = _publicSection.getOwnedClassifiers();
          Classifier _get = _ownedClassifiers.get(0);
          final Procedure1<Classifier> _function = new Procedure1<Classifier>() {
            public void apply(final Classifier it) {
              String _name = it.getName();
              Assert.assertEquals("a1", _name);
              EList<Prototype> _ownedPrototypes = it.getOwnedPrototypes();
              Prototype _head = IterableExtensions.<Prototype>head(_ownedPrototypes);
              final Procedure1<Prototype> _function = new Procedure1<Prototype>() {
                public void apply(final Prototype it) {
                  String _name = it.getName();
                  Assert.assertEquals("proto1", _name);
                  EReference _prototype_Refined = Aadl2Package.eINSTANCE.getPrototype_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototype_Refined, "");
                }
              };
              ObjectExtensions.<Prototype>operator_doubleArrow(_head, _function);
            }
          };
          ObjectExtensions.<Classifier>operator_doubleArrow(_get, _function);
          PublicPackageSection _publicSection_1 = it.getPublicSection();
          EList<Classifier> _ownedClassifiers_1 = _publicSection_1.getOwnedClassifiers();
          Classifier _get_1 = _ownedClassifiers_1.get(1);
          final Procedure1<AbstractType> _function_1 = new Procedure1<AbstractType>() {
            public void apply(final AbstractType it) {
              String _name = it.getName();
              Assert.assertEquals("a2", _name);
              EList<Prototype> _ownedPrototypes = it.getOwnedPrototypes();
              Prototype _head = IterableExtensions.<Prototype>head(_ownedPrototypes);
              final Procedure1<Prototype> _function = new Procedure1<Prototype>() {
                public void apply(final Prototype it) {
                  String _name = it.getName();
                  Assert.assertEquals("proto1", _name);
                  EReference _prototype_Refined = Aadl2Package.eINSTANCE.getPrototype_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototype_Refined, "proto1");
                }
              };
              ObjectExtensions.<Prototype>operator_doubleArrow(_head, _function);
              final String refinedFeatureScopeForA2 = "fg1, af1, ba1, da1, suba1, dport1, eport1, edport1, subga1";
              EList<DataPort> _ownedDataPorts = it.getOwnedDataPorts();
              DataPort _head_1 = IterableExtensions.<DataPort>head(_ownedDataPorts);
              final Procedure1<DataPort> _function_1 = new Procedure1<DataPort>() {
                public void apply(final DataPort it) {
                  String _name = it.getName();
                  Assert.assertEquals("dport1", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForA2);
                }
              };
              ObjectExtensions.<DataPort>operator_doubleArrow(_head_1, _function_1);
              EList<EventDataPort> _ownedEventDataPorts = it.getOwnedEventDataPorts();
              EventDataPort _head_2 = IterableExtensions.<EventDataPort>head(_ownedEventDataPorts);
              final Procedure1<EventDataPort> _function_2 = new Procedure1<EventDataPort>() {
                public void apply(final EventDataPort it) {
                  String _name = it.getName();
                  Assert.assertEquals("edport1", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForA2);
                }
              };
              ObjectExtensions.<EventDataPort>operator_doubleArrow(_head_2, _function_2);
              EList<EventPort> _ownedEventPorts = it.getOwnedEventPorts();
              EventPort _head_3 = IterableExtensions.<EventPort>head(_ownedEventPorts);
              final Procedure1<EventPort> _function_3 = new Procedure1<EventPort>() {
                public void apply(final EventPort it) {
                  String _name = it.getName();
                  Assert.assertEquals("eport1", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForA2);
                }
              };
              ObjectExtensions.<EventPort>operator_doubleArrow(_head_3, _function_3);
              EList<FeatureGroup> _ownedFeatureGroups = it.getOwnedFeatureGroups();
              FeatureGroup _head_4 = IterableExtensions.<FeatureGroup>head(_ownedFeatureGroups);
              final Procedure1<FeatureGroup> _function_4 = new Procedure1<FeatureGroup>() {
                public void apply(final FeatureGroup it) {
                  String _name = it.getName();
                  Assert.assertEquals("fg1", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForA2);
                }
              };
              ObjectExtensions.<FeatureGroup>operator_doubleArrow(_head_4, _function_4);
              EList<SubprogramAccess> _ownedSubprogramAccesses = it.getOwnedSubprogramAccesses();
              SubprogramAccess _head_5 = IterableExtensions.<SubprogramAccess>head(_ownedSubprogramAccesses);
              final Procedure1<SubprogramAccess> _function_5 = new Procedure1<SubprogramAccess>() {
                public void apply(final SubprogramAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("suba1", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForA2);
                }
              };
              ObjectExtensions.<SubprogramAccess>operator_doubleArrow(_head_5, _function_5);
              EList<SubprogramGroupAccess> _ownedSubprogramGroupAccesses = it.getOwnedSubprogramGroupAccesses();
              SubprogramGroupAccess _head_6 = IterableExtensions.<SubprogramGroupAccess>head(_ownedSubprogramGroupAccesses);
              final Procedure1<SubprogramGroupAccess> _function_6 = new Procedure1<SubprogramGroupAccess>() {
                public void apply(final SubprogramGroupAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("subga1", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForA2);
                }
              };
              ObjectExtensions.<SubprogramGroupAccess>operator_doubleArrow(_head_6, _function_6);
              EList<BusAccess> _ownedBusAccesses = it.getOwnedBusAccesses();
              BusAccess _head_7 = IterableExtensions.<BusAccess>head(_ownedBusAccesses);
              final Procedure1<BusAccess> _function_7 = new Procedure1<BusAccess>() {
                public void apply(final BusAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("ba1", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForA2);
                }
              };
              ObjectExtensions.<BusAccess>operator_doubleArrow(_head_7, _function_7);
              EList<DataAccess> _ownedDataAccesses = it.getOwnedDataAccesses();
              DataAccess _head_8 = IterableExtensions.<DataAccess>head(_ownedDataAccesses);
              final Procedure1<DataAccess> _function_8 = new Procedure1<DataAccess>() {
                public void apply(final DataAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("da1", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForA2);
                }
              };
              ObjectExtensions.<DataAccess>operator_doubleArrow(_head_8, _function_8);
              EList<AbstractFeature> _ownedAbstractFeatures = it.getOwnedAbstractFeatures();
              AbstractFeature _head_9 = IterableExtensions.<AbstractFeature>head(_ownedAbstractFeatures);
              final Procedure1<AbstractFeature> _function_9 = new Procedure1<AbstractFeature>() {
                public void apply(final AbstractFeature it) {
                  String _name = it.getName();
                  Assert.assertEquals("af1", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForA2);
                }
              };
              ObjectExtensions.<AbstractFeature>operator_doubleArrow(_head_9, _function_9);
            }
          };
          ObjectExtensions.<AbstractType>operator_doubleArrow(
            ((AbstractType) _get_1), _function_1);
          PublicPackageSection _publicSection_2 = it.getPublicSection();
          EList<Classifier> _ownedClassifiers_2 = _publicSection_2.getOwnedClassifiers();
          Classifier _get_2 = _ownedClassifiers_2.get(2);
          final Procedure1<Classifier> _function_2 = new Procedure1<Classifier>() {
            public void apply(final Classifier it) {
              String _name = it.getName();
              Assert.assertEquals("fgt1", _name);
              EList<Prototype> _ownedPrototypes = it.getOwnedPrototypes();
              Prototype _head = IterableExtensions.<Prototype>head(_ownedPrototypes);
              final Procedure1<Prototype> _function = new Procedure1<Prototype>() {
                public void apply(final Prototype it) {
                  String _name = it.getName();
                  Assert.assertEquals("proto2", _name);
                  EReference _prototype_Refined = Aadl2Package.eINSTANCE.getPrototype_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototype_Refined, "");
                }
              };
              ObjectExtensions.<Prototype>operator_doubleArrow(_head, _function);
            }
          };
          ObjectExtensions.<Classifier>operator_doubleArrow(_get_2, _function_2);
          PublicPackageSection _publicSection_3 = it.getPublicSection();
          EList<Classifier> _ownedClassifiers_3 = _publicSection_3.getOwnedClassifiers();
          Classifier _get_3 = _ownedClassifiers_3.get(3);
          final Procedure1<FeatureGroupType> _function_3 = new Procedure1<FeatureGroupType>() {
            public void apply(final FeatureGroupType it) {
              String _name = it.getName();
              Assert.assertEquals("fgt2", _name);
              EList<Prototype> _ownedPrototypes = it.getOwnedPrototypes();
              Prototype _head = IterableExtensions.<Prototype>head(_ownedPrototypes);
              final Procedure1<Prototype> _function = new Procedure1<Prototype>() {
                public void apply(final Prototype it) {
                  String _name = it.getName();
                  Assert.assertEquals("proto2", _name);
                  EReference _prototype_Refined = Aadl2Package.eINSTANCE.getPrototype_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototype_Refined, "proto2");
                }
              };
              ObjectExtensions.<Prototype>operator_doubleArrow(_head, _function);
              final String refinedFeatureScopeForFgt2 = "ba2, da2, dport2, edport2, eport2, fg2, param1, suba2, subga2, af2";
              EList<DataPort> _ownedDataPorts = it.getOwnedDataPorts();
              DataPort _head_1 = IterableExtensions.<DataPort>head(_ownedDataPorts);
              final Procedure1<DataPort> _function_1 = new Procedure1<DataPort>() {
                public void apply(final DataPort it) {
                  String _name = it.getName();
                  Assert.assertEquals("dport2", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForFgt2);
                }
              };
              ObjectExtensions.<DataPort>operator_doubleArrow(_head_1, _function_1);
              EList<EventDataPort> _ownedEventDataPorts = it.getOwnedEventDataPorts();
              EventDataPort _head_2 = IterableExtensions.<EventDataPort>head(_ownedEventDataPorts);
              final Procedure1<EventDataPort> _function_2 = new Procedure1<EventDataPort>() {
                public void apply(final EventDataPort it) {
                  String _name = it.getName();
                  Assert.assertEquals("edport2", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForFgt2);
                }
              };
              ObjectExtensions.<EventDataPort>operator_doubleArrow(_head_2, _function_2);
              EList<EventPort> _ownedEventPorts = it.getOwnedEventPorts();
              EventPort _head_3 = IterableExtensions.<EventPort>head(_ownedEventPorts);
              final Procedure1<EventPort> _function_3 = new Procedure1<EventPort>() {
                public void apply(final EventPort it) {
                  String _name = it.getName();
                  Assert.assertEquals("eport2", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForFgt2);
                }
              };
              ObjectExtensions.<EventPort>operator_doubleArrow(_head_3, _function_3);
              EList<FeatureGroup> _ownedFeatureGroups = it.getOwnedFeatureGroups();
              FeatureGroup _head_4 = IterableExtensions.<FeatureGroup>head(_ownedFeatureGroups);
              final Procedure1<FeatureGroup> _function_4 = new Procedure1<FeatureGroup>() {
                public void apply(final FeatureGroup it) {
                  String _name = it.getName();
                  Assert.assertEquals("fg2", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForFgt2);
                }
              };
              ObjectExtensions.<FeatureGroup>operator_doubleArrow(_head_4, _function_4);
              EList<Parameter> _ownedParameters = it.getOwnedParameters();
              Parameter _head_5 = IterableExtensions.<Parameter>head(_ownedParameters);
              final Procedure1<Parameter> _function_5 = new Procedure1<Parameter>() {
                public void apply(final Parameter it) {
                  String _name = it.getName();
                  Assert.assertEquals("param1", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForFgt2);
                }
              };
              ObjectExtensions.<Parameter>operator_doubleArrow(_head_5, _function_5);
              EList<SubprogramAccess> _ownedSubprogramAccesses = it.getOwnedSubprogramAccesses();
              SubprogramAccess _head_6 = IterableExtensions.<SubprogramAccess>head(_ownedSubprogramAccesses);
              final Procedure1<SubprogramAccess> _function_6 = new Procedure1<SubprogramAccess>() {
                public void apply(final SubprogramAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("suba2", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForFgt2);
                }
              };
              ObjectExtensions.<SubprogramAccess>operator_doubleArrow(_head_6, _function_6);
              EList<SubprogramGroupAccess> _ownedSubprogramGroupAccesses = it.getOwnedSubprogramGroupAccesses();
              SubprogramGroupAccess _head_7 = IterableExtensions.<SubprogramGroupAccess>head(_ownedSubprogramGroupAccesses);
              final Procedure1<SubprogramGroupAccess> _function_7 = new Procedure1<SubprogramGroupAccess>() {
                public void apply(final SubprogramGroupAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("subga2", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForFgt2);
                }
              };
              ObjectExtensions.<SubprogramGroupAccess>operator_doubleArrow(_head_7, _function_7);
              EList<BusAccess> _ownedBusAccesses = it.getOwnedBusAccesses();
              BusAccess _head_8 = IterableExtensions.<BusAccess>head(_ownedBusAccesses);
              final Procedure1<BusAccess> _function_8 = new Procedure1<BusAccess>() {
                public void apply(final BusAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("ba2", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForFgt2);
                }
              };
              ObjectExtensions.<BusAccess>operator_doubleArrow(_head_8, _function_8);
              EList<DataAccess> _ownedDataAccesses = it.getOwnedDataAccesses();
              DataAccess _head_9 = IterableExtensions.<DataAccess>head(_ownedDataAccesses);
              final Procedure1<DataAccess> _function_9 = new Procedure1<DataAccess>() {
                public void apply(final DataAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("da2", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForFgt2);
                }
              };
              ObjectExtensions.<DataAccess>operator_doubleArrow(_head_9, _function_9);
              EList<AbstractFeature> _ownedAbstractFeatures = it.getOwnedAbstractFeatures();
              AbstractFeature _head_10 = IterableExtensions.<AbstractFeature>head(_ownedAbstractFeatures);
              final Procedure1<AbstractFeature> _function_10 = new Procedure1<AbstractFeature>() {
                public void apply(final AbstractFeature it) {
                  String _name = it.getName();
                  Assert.assertEquals("af2", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForFgt2);
                }
              };
              ObjectExtensions.<AbstractFeature>operator_doubleArrow(_head_10, _function_10);
            }
          };
          ObjectExtensions.<FeatureGroupType>operator_doubleArrow(
            ((FeatureGroupType) _get_3), _function_3);
          PublicPackageSection _publicSection_4 = it.getPublicSection();
          EList<Classifier> _ownedClassifiers_4 = _publicSection_4.getOwnedClassifiers();
          Classifier _get_4 = _ownedClassifiers_4.get(5);
          final Procedure1<ComponentImplementation> _function_4 = new Procedure1<ComponentImplementation>() {
            public void apply(final ComponentImplementation it) {
              String _name = it.getName();
              Assert.assertEquals("a1.i2", _name);
              EList<Subcomponent> _ownedSubcomponents = it.getOwnedSubcomponents();
              Subcomponent _head = IterableExtensions.<Subcomponent>head(_ownedSubcomponents);
              final Procedure1<Subcomponent> _function = new Procedure1<Subcomponent>() {
                public void apply(final Subcomponent it) {
                  String _name = it.getName();
                  Assert.assertEquals("asub1", _name);
                  EReference _subcomponent_Refined = Aadl2Package.eINSTANCE.getSubcomponent_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _subcomponent_Refined, "asub1");
                }
              };
              ObjectExtensions.<Subcomponent>operator_doubleArrow(_head, _function);
            }
          };
          ObjectExtensions.<ComponentImplementation>operator_doubleArrow(
            ((ComponentImplementation) _get_4), _function_4);
          PublicPackageSection _publicSection_5 = it.getPublicSection();
          EList<Classifier> _ownedClassifiers_5 = _publicSection_5.getOwnedClassifiers();
          Classifier _get_5 = _ownedClassifiers_5.get(7);
          final Procedure1<SubprogramType> _function_5 = new Procedure1<SubprogramType>() {
            public void apply(final SubprogramType it) {
              String _name = it.getName();
              Assert.assertEquals("sub2", _name);
              final String refinedFeatureScopeForSub2 = "fg3, af3, edport3, eport3, param2, da3, suba3, subga3";
              EList<EventDataPort> _ownedEventDataPorts = it.getOwnedEventDataPorts();
              EventDataPort _head = IterableExtensions.<EventDataPort>head(_ownedEventDataPorts);
              final Procedure1<EventDataPort> _function = new Procedure1<EventDataPort>() {
                public void apply(final EventDataPort it) {
                  String _name = it.getName();
                  Assert.assertEquals("edport3", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForSub2);
                }
              };
              ObjectExtensions.<EventDataPort>operator_doubleArrow(_head, _function);
              EList<EventPort> _ownedEventPorts = it.getOwnedEventPorts();
              EventPort _head_1 = IterableExtensions.<EventPort>head(_ownedEventPorts);
              final Procedure1<EventPort> _function_1 = new Procedure1<EventPort>() {
                public void apply(final EventPort it) {
                  String _name = it.getName();
                  Assert.assertEquals("eport3", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForSub2);
                }
              };
              ObjectExtensions.<EventPort>operator_doubleArrow(_head_1, _function_1);
              EList<FeatureGroup> _ownedFeatureGroups = it.getOwnedFeatureGroups();
              FeatureGroup _head_2 = IterableExtensions.<FeatureGroup>head(_ownedFeatureGroups);
              final Procedure1<FeatureGroup> _function_2 = new Procedure1<FeatureGroup>() {
                public void apply(final FeatureGroup it) {
                  String _name = it.getName();
                  Assert.assertEquals("fg3", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForSub2);
                }
              };
              ObjectExtensions.<FeatureGroup>operator_doubleArrow(_head_2, _function_2);
              EList<Parameter> _ownedParameters = it.getOwnedParameters();
              Parameter _head_3 = IterableExtensions.<Parameter>head(_ownedParameters);
              final Procedure1<Parameter> _function_3 = new Procedure1<Parameter>() {
                public void apply(final Parameter it) {
                  String _name = it.getName();
                  Assert.assertEquals("param2", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForSub2);
                }
              };
              ObjectExtensions.<Parameter>operator_doubleArrow(_head_3, _function_3);
              EList<SubprogramAccess> _ownedSubprogramAccesses = it.getOwnedSubprogramAccesses();
              SubprogramAccess _head_4 = IterableExtensions.<SubprogramAccess>head(_ownedSubprogramAccesses);
              final Procedure1<SubprogramAccess> _function_4 = new Procedure1<SubprogramAccess>() {
                public void apply(final SubprogramAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("suba3", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForSub2);
                }
              };
              ObjectExtensions.<SubprogramAccess>operator_doubleArrow(_head_4, _function_4);
              EList<SubprogramGroupAccess> _ownedSubprogramGroupAccesses = it.getOwnedSubprogramGroupAccesses();
              SubprogramGroupAccess _head_5 = IterableExtensions.<SubprogramGroupAccess>head(_ownedSubprogramGroupAccesses);
              final Procedure1<SubprogramGroupAccess> _function_5 = new Procedure1<SubprogramGroupAccess>() {
                public void apply(final SubprogramGroupAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("subga3", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForSub2);
                }
              };
              ObjectExtensions.<SubprogramGroupAccess>operator_doubleArrow(_head_5, _function_5);
              EList<DataAccess> _ownedDataAccesses = it.getOwnedDataAccesses();
              DataAccess _head_6 = IterableExtensions.<DataAccess>head(_ownedDataAccesses);
              final Procedure1<DataAccess> _function_6 = new Procedure1<DataAccess>() {
                public void apply(final DataAccess it) {
                  String _name = it.getName();
                  Assert.assertEquals("da3", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForSub2);
                }
              };
              ObjectExtensions.<DataAccess>operator_doubleArrow(_head_6, _function_6);
              EList<AbstractFeature> _ownedAbstractFeatures = it.getOwnedAbstractFeatures();
              AbstractFeature _head_7 = IterableExtensions.<AbstractFeature>head(_ownedAbstractFeatures);
              final Procedure1<AbstractFeature> _function_7 = new Procedure1<AbstractFeature>() {
                public void apply(final AbstractFeature it) {
                  String _name = it.getName();
                  Assert.assertEquals("af3", _name);
                  EReference _feature_Refined = Aadl2Package.eINSTANCE.getFeature_Refined();
                  Aadl2ScopeProviderTest.this.assertScope(it, _feature_Refined, refinedFeatureScopeForSub2);
                }
              };
              ObjectExtensions.<AbstractFeature>operator_doubleArrow(_head_7, _function_7);
            }
          };
          ObjectExtensions.<SubprogramType>operator_doubleArrow(
            ((SubprogramType) _get_5), _function_5);
        }
      };
      ObjectExtensions.<AadlPackage>operator_doubleArrow(((AadlPackage) _parse), _function);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Tests scope_PrototypeBinding_formal, scope_FeatureGroupPrototypeActual_featureType, scope_FeaturePrototypeReference_prototype, and
   * scope_ComponentPrototypeActual_subcomponentType
   */
  @Test
  public void testPrototypeBindings() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package pack");
      _builder.newLine();
      _builder.append("public");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("abstract a1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("prototypes");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto1: abstract;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto3: feature group;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto5: feature group;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto8: feature;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto9: feature;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto11: abstract;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end a1;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("abstract a2 extends a1 (");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto1 => abstract a3 (");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("proto2 => in data port");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("),");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto3 => feature group fgt1 (");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("proto4 => in data port");
      _builder.newLine();
      _builder.append("    ");
      _builder.append(")");
      _builder.newLine();
      _builder.append("  ");
      _builder.append(")");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end a2;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("abstract implementation a1.i1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("subcomponents");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("asub1: abstract [](a3.i1 (");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("proto2 => in data port");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("));");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("asub2: abstract a3 (");
      _builder.newLine();
      _builder.append("      ");
      _builder.append("proto2 => in data port");
      _builder.newLine();
      _builder.append("    ");
      _builder.append(");");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end a1.i1;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("abstract implementation a1.i2 extends a1.i1 (");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto1 => abstract a3,");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto3 => feature group fgt1,");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto5 => feature group proto3,");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto8 => feature proto9,");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto11 => abstract proto1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append(")");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end a1.i2;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("abstract a3");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("prototypes");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto2: feature;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end a3;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("abstract implementation a3.i1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end a3.i1;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("feature group fgt1");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("prototypes");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto4: feature;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto6: feature group;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto10: feature;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto12: abstract;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end fgt1;");
      _builder.newLine();
      _builder.append("  ");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("feature group fgt2 extends fgt1 (");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto4 => in data port,");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto6 => feature group proto7,");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto10 => feature proto4,");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto12 => abstract proto13");
      _builder.newLine();
      _builder.append("  ");
      _builder.append(")");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("prototypes");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto7: feature group;");
      _builder.newLine();
      _builder.append("    ");
      _builder.append("proto13: abstract;");
      _builder.newLine();
      _builder.append("  ");
      _builder.append("end fgt2;");
      _builder.newLine();
      _builder.append("end pack;");
      _builder.newLine();
      ModelUnit _parse = this._parseHelper.parse(_builder);
      final Procedure1<AadlPackage> _function = new Procedure1<AadlPackage>() {
        public void apply(final AadlPackage it) {
          String _name = it.getName();
          Assert.assertEquals("pack", _name);
          PublicPackageSection _publicSection = it.getPublicSection();
          EList<Classifier> _ownedClassifiers = _publicSection.getOwnedClassifiers();
          Classifier _get = _ownedClassifiers.get(1);
          final Procedure1<Classifier> _function = new Procedure1<Classifier>() {
            public void apply(final Classifier it) {
              String _name = it.getName();
              Assert.assertEquals("a2", _name);
              EList<PrototypeBinding> _ownedPrototypeBindings = it.getOwnedPrototypeBindings();
              PrototypeBinding _get = _ownedPrototypeBindings.get(0);
              final Procedure1<ComponentPrototypeBinding> _function = new Procedure1<ComponentPrototypeBinding>() {
                public void apply(final ComponentPrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto1", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11");
                  EList<ComponentPrototypeActual> _actuals = it.getActuals();
                  ComponentPrototypeActual _head = IterableExtensions.<ComponentPrototypeActual>head(_actuals);
                  final Procedure1<ComponentPrototypeActual> _function = new Procedure1<ComponentPrototypeActual>() {
                    public void apply(final ComponentPrototypeActual it) {
                      SubcomponentType _subcomponentType = it.getSubcomponentType();
                      String _name = _subcomponentType.getName();
                      Assert.assertEquals("a3", _name);
                      EList<PrototypeBinding> _bindings = it.getBindings();
                      PrototypeBinding _head = IterableExtensions.<PrototypeBinding>head(_bindings);
                      final Procedure1<PrototypeBinding> _function = new Procedure1<PrototypeBinding>() {
                        public void apply(final PrototypeBinding it) {
                          Prototype _formal = it.getFormal();
                          String _name = _formal.getName();
                          Assert.assertEquals("proto2", _name);
                          EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                          Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto2");
                        }
                      };
                      ObjectExtensions.<PrototypeBinding>operator_doubleArrow(_head, _function);
                    }
                  };
                  ObjectExtensions.<ComponentPrototypeActual>operator_doubleArrow(_head, _function);
                }
              };
              ObjectExtensions.<ComponentPrototypeBinding>operator_doubleArrow(
                ((ComponentPrototypeBinding) _get), _function);
              EList<PrototypeBinding> _ownedPrototypeBindings_1 = it.getOwnedPrototypeBindings();
              PrototypeBinding _get_1 = _ownedPrototypeBindings_1.get(1);
              final Procedure1<FeatureGroupPrototypeBinding> _function_1 = new Procedure1<FeatureGroupPrototypeBinding>() {
                public void apply(final FeatureGroupPrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto3", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11");
                  FeatureGroupPrototypeActual _actual = it.getActual();
                  final Procedure1<FeatureGroupPrototypeActual> _function = new Procedure1<FeatureGroupPrototypeActual>() {
                    public void apply(final FeatureGroupPrototypeActual it) {
                      FeatureType _featureType = it.getFeatureType();
                      String _name = ((NamedElement) _featureType).getName();
                      Assert.assertEquals("fgt1", _name);
                      EReference _featureGroupPrototypeActual_FeatureType = Aadl2Package.eINSTANCE.getFeatureGroupPrototypeActual_FeatureType();
                      Aadl2ScopeProviderTest.this.assertScope(it, _featureGroupPrototypeActual_FeatureType, "proto3, proto5, fgt1, fgt2, pack.fgt1, pack.fgt2");
                      EList<PrototypeBinding> _bindings = it.getBindings();
                      PrototypeBinding _head = IterableExtensions.<PrototypeBinding>head(_bindings);
                      final Procedure1<PrototypeBinding> _function = new Procedure1<PrototypeBinding>() {
                        public void apply(final PrototypeBinding it) {
                          Prototype _formal = it.getFormal();
                          String _name = _formal.getName();
                          Assert.assertEquals("proto4", _name);
                          EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                          Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto4, proto6, proto10, proto12");
                        }
                      };
                      ObjectExtensions.<PrototypeBinding>operator_doubleArrow(_head, _function);
                    }
                  };
                  ObjectExtensions.<FeatureGroupPrototypeActual>operator_doubleArrow(_actual, _function);
                }
              };
              ObjectExtensions.<FeatureGroupPrototypeBinding>operator_doubleArrow(
                ((FeatureGroupPrototypeBinding) _get_1), _function_1);
            }
          };
          ObjectExtensions.<Classifier>operator_doubleArrow(_get, _function);
          PublicPackageSection _publicSection_1 = it.getPublicSection();
          EList<Classifier> _ownedClassifiers_1 = _publicSection_1.getOwnedClassifiers();
          Classifier _get_1 = _ownedClassifiers_1.get(2);
          final Procedure1<ComponentImplementation> _function_1 = new Procedure1<ComponentImplementation>() {
            public void apply(final ComponentImplementation it) {
              String _name = it.getName();
              Assert.assertEquals("a1.i1", _name);
              EList<Subcomponent> _ownedSubcomponents = it.getOwnedSubcomponents();
              Subcomponent _get = _ownedSubcomponents.get(0);
              final Procedure1<Subcomponent> _function = new Procedure1<Subcomponent>() {
                public void apply(final Subcomponent it) {
                  String _name = it.getName();
                  Assert.assertEquals("asub1", _name);
                  EList<ComponentImplementationReference> _implementationReferences = it.getImplementationReferences();
                  ComponentImplementationReference _head = IterableExtensions.<ComponentImplementationReference>head(_implementationReferences);
                  final Procedure1<ComponentImplementationReference> _function = new Procedure1<ComponentImplementationReference>() {
                    public void apply(final ComponentImplementationReference it) {
                      ComponentImplementation _implementation = it.getImplementation();
                      String _name = _implementation.getName();
                      Assert.assertEquals("a3.i1", _name);
                      EList<PrototypeBinding> _ownedPrototypeBindings = it.getOwnedPrototypeBindings();
                      PrototypeBinding _head = IterableExtensions.<PrototypeBinding>head(_ownedPrototypeBindings);
                      final Procedure1<PrototypeBinding> _function = new Procedure1<PrototypeBinding>() {
                        public void apply(final PrototypeBinding it) {
                          Prototype _formal = it.getFormal();
                          String _name = _formal.getName();
                          Assert.assertEquals("proto2", _name);
                          EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                          Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto2");
                        }
                      };
                      ObjectExtensions.<PrototypeBinding>operator_doubleArrow(_head, _function);
                    }
                  };
                  ObjectExtensions.<ComponentImplementationReference>operator_doubleArrow(_head, _function);
                }
              };
              ObjectExtensions.<Subcomponent>operator_doubleArrow(_get, _function);
              EList<Subcomponent> _ownedSubcomponents_1 = it.getOwnedSubcomponents();
              Subcomponent _get_1 = _ownedSubcomponents_1.get(1);
              final Procedure1<Subcomponent> _function_1 = new Procedure1<Subcomponent>() {
                public void apply(final Subcomponent it) {
                  String _name = it.getName();
                  Assert.assertEquals("asub2", _name);
                  EList<PrototypeBinding> _ownedPrototypeBindings = it.getOwnedPrototypeBindings();
                  PrototypeBinding _head = IterableExtensions.<PrototypeBinding>head(_ownedPrototypeBindings);
                  final Procedure1<PrototypeBinding> _function = new Procedure1<PrototypeBinding>() {
                    public void apply(final PrototypeBinding it) {
                      Prototype _formal = it.getFormal();
                      String _name = _formal.getName();
                      Assert.assertEquals("proto2", _name);
                      EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                      Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto2");
                    }
                  };
                  ObjectExtensions.<PrototypeBinding>operator_doubleArrow(_head, _function);
                }
              };
              ObjectExtensions.<Subcomponent>operator_doubleArrow(_get_1, _function_1);
            }
          };
          ObjectExtensions.<ComponentImplementation>operator_doubleArrow(
            ((ComponentImplementation) _get_1), _function_1);
          PublicPackageSection _publicSection_2 = it.getPublicSection();
          EList<Classifier> _ownedClassifiers_2 = _publicSection_2.getOwnedClassifiers();
          Classifier _get_2 = _ownedClassifiers_2.get(3);
          final Procedure1<Classifier> _function_2 = new Procedure1<Classifier>() {
            public void apply(final Classifier it) {
              String _name = it.getName();
              Assert.assertEquals("a1.i2", _name);
              EList<PrototypeBinding> _ownedPrototypeBindings = it.getOwnedPrototypeBindings();
              PrototypeBinding _get = _ownedPrototypeBindings.get(0);
              final Procedure1<ComponentPrototypeBinding> _function = new Procedure1<ComponentPrototypeBinding>() {
                public void apply(final ComponentPrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto1", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11");
                  EList<ComponentPrototypeActual> _actuals = it.getActuals();
                  ComponentPrototypeActual _head = IterableExtensions.<ComponentPrototypeActual>head(_actuals);
                  EReference _componentPrototypeActual_SubcomponentType = Aadl2Package.eINSTANCE.getComponentPrototypeActual_SubcomponentType();
                  Aadl2ScopeProviderTest.this.assertScope(_head, _componentPrototypeActual_SubcomponentType, ("proto1, proto11, a1, a2, a1.i1, a1.i2, a3, " + 
                    "a3.i1, pack.a1, pack.a2, pack.a1.i1, pack.a1.i2, pack.a3, pack.a3.i1"));
                }
              };
              ObjectExtensions.<ComponentPrototypeBinding>operator_doubleArrow(
                ((ComponentPrototypeBinding) _get), _function);
              EList<PrototypeBinding> _ownedPrototypeBindings_1 = it.getOwnedPrototypeBindings();
              PrototypeBinding _get_1 = _ownedPrototypeBindings_1.get(1);
              final Procedure1<FeatureGroupPrototypeBinding> _function_1 = new Procedure1<FeatureGroupPrototypeBinding>() {
                public void apply(final FeatureGroupPrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto3", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11");
                  FeatureGroupPrototypeActual _actual = it.getActual();
                  EReference _featureGroupPrototypeActual_FeatureType = Aadl2Package.eINSTANCE.getFeatureGroupPrototypeActual_FeatureType();
                  Aadl2ScopeProviderTest.this.assertScope(_actual, _featureGroupPrototypeActual_FeatureType, "proto3, proto5, fgt1, fgt2, pack.fgt1, pack.fgt2");
                }
              };
              ObjectExtensions.<FeatureGroupPrototypeBinding>operator_doubleArrow(
                ((FeatureGroupPrototypeBinding) _get_1), _function_1);
              EList<PrototypeBinding> _ownedPrototypeBindings_2 = it.getOwnedPrototypeBindings();
              PrototypeBinding _get_2 = _ownedPrototypeBindings_2.get(2);
              final Procedure1<FeatureGroupPrototypeBinding> _function_2 = new Procedure1<FeatureGroupPrototypeBinding>() {
                public void apply(final FeatureGroupPrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto5", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11");
                  FeatureGroupPrototypeActual _actual = it.getActual();
                  EReference _featureGroupPrototypeActual_FeatureType = Aadl2Package.eINSTANCE.getFeatureGroupPrototypeActual_FeatureType();
                  Aadl2ScopeProviderTest.this.assertScope(_actual, _featureGroupPrototypeActual_FeatureType, "proto3, proto5, fgt1, fgt2, pack.fgt1, pack.fgt2");
                }
              };
              ObjectExtensions.<FeatureGroupPrototypeBinding>operator_doubleArrow(
                ((FeatureGroupPrototypeBinding) _get_2), _function_2);
              EList<PrototypeBinding> _ownedPrototypeBindings_3 = it.getOwnedPrototypeBindings();
              PrototypeBinding _get_3 = _ownedPrototypeBindings_3.get(3);
              final Procedure1<FeaturePrototypeBinding> _function_3 = new Procedure1<FeaturePrototypeBinding>() {
                public void apply(final FeaturePrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto8", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11");
                  FeaturePrototypeActual _actual = it.getActual();
                  EReference _featurePrototypeReference_Prototype = Aadl2Package.eINSTANCE.getFeaturePrototypeReference_Prototype();
                  Aadl2ScopeProviderTest.this.assertScope(_actual, _featurePrototypeReference_Prototype, "proto8, proto9");
                }
              };
              ObjectExtensions.<FeaturePrototypeBinding>operator_doubleArrow(
                ((FeaturePrototypeBinding) _get_3), _function_3);
              EList<PrototypeBinding> _ownedPrototypeBindings_4 = it.getOwnedPrototypeBindings();
              PrototypeBinding _get_4 = _ownedPrototypeBindings_4.get(4);
              final Procedure1<ComponentPrototypeBinding> _function_4 = new Procedure1<ComponentPrototypeBinding>() {
                public void apply(final ComponentPrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto11", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto1, proto3, proto5, proto8, proto9, proto11");
                  EList<ComponentPrototypeActual> _actuals = it.getActuals();
                  ComponentPrototypeActual _head = IterableExtensions.<ComponentPrototypeActual>head(_actuals);
                  EReference _componentPrototypeActual_SubcomponentType = Aadl2Package.eINSTANCE.getComponentPrototypeActual_SubcomponentType();
                  Aadl2ScopeProviderTest.this.assertScope(_head, _componentPrototypeActual_SubcomponentType, ("proto1, proto11, a1, a2, a1.i1, a1.i2, a3, " + 
                    "a3.i1, pack.a1, pack.a2, pack.a1.i1, pack.a1.i2, pack.a3, pack.a3.i1"));
                }
              };
              ObjectExtensions.<ComponentPrototypeBinding>operator_doubleArrow(
                ((ComponentPrototypeBinding) _get_4), _function_4);
            }
          };
          ObjectExtensions.<Classifier>operator_doubleArrow(_get_2, _function_2);
          PublicPackageSection _publicSection_3 = it.getPublicSection();
          EList<Classifier> _ownedClassifiers_3 = _publicSection_3.getOwnedClassifiers();
          Classifier _get_3 = _ownedClassifiers_3.get(7);
          final Procedure1<Classifier> _function_3 = new Procedure1<Classifier>() {
            public void apply(final Classifier it) {
              String _name = it.getName();
              Assert.assertEquals("fgt2", _name);
              EList<PrototypeBinding> _ownedPrototypeBindings = it.getOwnedPrototypeBindings();
              PrototypeBinding _get = _ownedPrototypeBindings.get(0);
              final Procedure1<PrototypeBinding> _function = new Procedure1<PrototypeBinding>() {
                public void apply(final PrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto4", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto4, proto6, proto10, proto12");
                }
              };
              ObjectExtensions.<PrototypeBinding>operator_doubleArrow(_get, _function);
              EList<PrototypeBinding> _ownedPrototypeBindings_1 = it.getOwnedPrototypeBindings();
              PrototypeBinding _get_1 = _ownedPrototypeBindings_1.get(1);
              final Procedure1<FeatureGroupPrototypeBinding> _function_1 = new Procedure1<FeatureGroupPrototypeBinding>() {
                public void apply(final FeatureGroupPrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto6", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto4, proto6, proto10, proto12");
                  FeatureGroupPrototypeActual _actual = it.getActual();
                  EReference _featureGroupPrototypeActual_FeatureType = Aadl2Package.eINSTANCE.getFeatureGroupPrototypeActual_FeatureType();
                  Aadl2ScopeProviderTest.this.assertScope(_actual, _featureGroupPrototypeActual_FeatureType, "proto6, proto7, fgt1, fgt2, pack.fgt1, pack.fgt2");
                }
              };
              ObjectExtensions.<FeatureGroupPrototypeBinding>operator_doubleArrow(
                ((FeatureGroupPrototypeBinding) _get_1), _function_1);
              EList<PrototypeBinding> _ownedPrototypeBindings_2 = it.getOwnedPrototypeBindings();
              PrototypeBinding _get_2 = _ownedPrototypeBindings_2.get(2);
              final Procedure1<FeaturePrototypeBinding> _function_2 = new Procedure1<FeaturePrototypeBinding>() {
                public void apply(final FeaturePrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto10", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto4, proto6, proto10, proto12");
                  FeaturePrototypeActual _actual = it.getActual();
                  EReference _featurePrototypeReference_Prototype = Aadl2Package.eINSTANCE.getFeaturePrototypeReference_Prototype();
                  Aadl2ScopeProviderTest.this.assertScope(_actual, _featurePrototypeReference_Prototype, "proto4, proto10");
                }
              };
              ObjectExtensions.<FeaturePrototypeBinding>operator_doubleArrow(
                ((FeaturePrototypeBinding) _get_2), _function_2);
              EList<PrototypeBinding> _ownedPrototypeBindings_3 = it.getOwnedPrototypeBindings();
              PrototypeBinding _get_3 = _ownedPrototypeBindings_3.get(3);
              final Procedure1<ComponentPrototypeBinding> _function_3 = new Procedure1<ComponentPrototypeBinding>() {
                public void apply(final ComponentPrototypeBinding it) {
                  Prototype _formal = it.getFormal();
                  String _name = _formal.getName();
                  Assert.assertEquals("proto12", _name);
                  EReference _prototypeBinding_Formal = Aadl2Package.eINSTANCE.getPrototypeBinding_Formal();
                  Aadl2ScopeProviderTest.this.assertScope(it, _prototypeBinding_Formal, "proto4, proto6, proto10, proto12");
                  EList<ComponentPrototypeActual> _actuals = it.getActuals();
                  ComponentPrototypeActual _head = IterableExtensions.<ComponentPrototypeActual>head(_actuals);
                  EReference _componentPrototypeActual_SubcomponentType = Aadl2Package.eINSTANCE.getComponentPrototypeActual_SubcomponentType();
                  Aadl2ScopeProviderTest.this.assertScope(_head, _componentPrototypeActual_SubcomponentType, ("proto12, proto13, a1, a2, a1.i1, a1.i2, a3, " + 
                    "a3.i1, pack.a1, pack.a2, pack.a1.i1, pack.a1.i2, pack.a3, pack.a3.i1"));
                }
              };
              ObjectExtensions.<ComponentPrototypeBinding>operator_doubleArrow(
                ((ComponentPrototypeBinding) _get_3), _function_3);
            }
          };
          ObjectExtensions.<Classifier>operator_doubleArrow(_get_3, _function_3);
        }
      };
      ObjectExtensions.<AadlPackage>operator_doubleArrow(((AadlPackage) _parse), _function);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Tests scope_AbstractSubcomponent_abstractSubcomponentType, scope_SystemSubcomponent_systemSubcomponentType,
   * scope_ProcessSubcomponent_processSubcomponentType, scope_ThreadGroupSubcomponent_threadGroupSubcomponentType,
   * scope_ThreadSubcomponent_threadSubcomponentType, scope_SubprogramSubcomponent_subprogramSubcomponentType,
   * scope_SubprogramGroupSubcomponent_subprogramGroupSubcomponentType, scope_ProcessorSubcomponent_processorSubcomponentType,
   * scope_VirtualProcessorSubcomponent_virtualProcessorSubcomponentType, scope_DeviceSubcomponent_deviceSubcomponentType,
   * scope_MemorySubcomponent_memorySubcomponentType, scope_BusSubcomponent_busSubcomponentType, scope_VirtualBusSubcomponent_virtualBusSubcomponentType,
   * scope_DataSubcomponent_dataSubcomponentType, scope_DataPort_dataFeatureClassifier, scope_EventDataPort_dataFeatureClassifier,
   * scope_FeatureGroup_featureType, scope_Parameter_dataFeatureClassifier, scope_SubprogramAccess_subprogramFeatureClassifier,
   * scope_SubprogramGroupAccess_subprogramGroupFeatureClassifier, scope_BusAccess_busFeatureClassifier, scope_DataAccess_dataFeatureClassifier,
   * and scope_AbstractFeature_featurePrototype
   */
  @Test
  public void testFeaturesAndSubcomponents() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package pack1");
    _builder.newLine();
    _builder.append("public");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("with pack3;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("with pack4;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("with pack5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames pack3::all;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renamed_package renames package pack4;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames abstract pack5::a5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames system pack5::s5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames process pack5::p5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames thread group pack5::tg5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames thread pack5::t5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames subprogram pack5::sub5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames subprogram group pack5::subg5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames processor pack5::proc5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames virtual processor pack5::vp5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames device pack5::dev5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames memory pack5::m5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames bus pack5::b5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames virtual bus pack5::vb5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames data pack5::d5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("renames feature group pack5::fgt5;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("ra renames abstract pack5::a6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rs renames system pack5::s6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rp renames process pack5::p6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rtg renames thread group pack5::tg6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rt renames thread pack5::t6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rsub renames subprogram pack5::sub6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rsubg renames subprogram group pack5::subg6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rproc renames processor pack5::proc6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rvp renames virtual processor pack5::vp6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rdev renames device pack5::dev6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rm renames memory pack5::m6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rb renames bus pack5::b6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rvb renames virtual bus pack5::vb6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rd renames data pack5::d6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("rfgt renames feature group pack5::fgt6;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("abstract container");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("prototypes");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("aproto1: abstract;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("sproto1: system;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("pproto1: process;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("tgproto1: thread group;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("tproto1: thread;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("subproto1: subprogram;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("subgproto1: subprogram group;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("procproto1: processor;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("vpproto1: virtual processor;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("devproto1: device;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("mproto1: memory;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bproto1: bus;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("vbproto1: virtual bus;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("dproto1: data;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("fgproto1: feature group;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("fproto1: feature;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("features");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("dport1: in data port d1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("edport1: in event data port d1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("fg1: feature group fgt1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("suba1: provides subprogram access sub1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("subga1: provides subprogram group access subg1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ba1: provides bus access b1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("da1: provides data access d1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("af1: feature fproto1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end container;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("abstract implementation container.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("subcomponents");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("asub: abstract a1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ssub: system s1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("psub: process p1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("tgsub: thread group tg1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("tsub: thread t1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("subsub: subprogram sub1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("subgsub: subprogram group subg1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("procsub: processor proc1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("vpsub: virtual processor vp1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("devsub: device dev1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("msub: memory m1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bsub: bus b1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("vbsub: virtual bus vb1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("dsub: data d1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end container.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("subprogram subcontainer");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("prototypes");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("aproto2: abstract;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("sproto2: system;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("pproto2: process;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("tgproto2: thread group;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("tproto2: thread;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("subproto2: subprogram;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("subgproto2: subprogram group;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("procproto2: processor;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("vpproto2: virtual processor;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("devproto2: device;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("mproto2: memory;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bproto2: bus;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("vbproto2: virtual bus;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("dproto2: data;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("fgproto2: feature group;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("fproto2: feature;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("features");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("param1: in parameter d1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end subcontainer;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("feature group fgtcontainer");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("prototypes");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("aproto3: abstract;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("sproto3: system;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("pproto3: process;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("tgproto3: thread group;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("tproto3: thread;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("subproto3: subprogram;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("subgproto3: subprogram group;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("procproto3: processor;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("vpproto3: virtual processor;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("devproto3: device;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("mproto3: memory;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bproto3: bus;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("vbproto3: virtual bus;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("dproto3: data;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("fgproto3: feature group;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("fproto3: feature;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("features");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("dport2: in data port d1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("edport2: in event data port d1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("fg2: feature group fgt1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("param2: in parameter d1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("suba2: provides subprogram access sub1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("subga2: provides subprogram group access subg1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ba2: provides bus access b1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("da2: provides data access d1;");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("af2: feature fproto3;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end fgtcontainer;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("abstract a1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end a1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("abstract implementation a1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end a1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("system s1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end s1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("system implementation s1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end s1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("process p1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end p1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("process implementation p1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end p1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("thread group tg1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end tg1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("thread group implementation tg1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end tg1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("thread t1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end t1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("thread implementation t1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end t1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("subprogram sub1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end sub1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("subprogram implementation sub1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end sub1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("subprogram group subg1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end subg1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("subprogram group implementation subg1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end subg1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("processor proc1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end proc1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("processor implementation proc1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end proc1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("virtual processor vp1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end vp1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("virtual processor implementation vp1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end vp1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("device dev1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end dev1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("device implementation dev1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end dev1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("memory m1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end m1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("memory implementation m1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end m1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("bus b1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end b1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("bus implementation b1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end b1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("virtual bus vb1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end vb1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("virtual bus implementation vb1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end vb1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("data d1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end d1;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("data implementation d1.i");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end d1.i;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("feature group fgt1");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end fgt1;");
    _builder.newLine();
    _builder.append("end pack1;");
    _builder.newLine();
    Pair<String, String> _mappedTo = Pair.<String, String>of("pack1.aadl", _builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("package pack2");
    _builder_1.newLine();
    _builder_1.append("public");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("abstract a2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end a2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("abstract implementation a2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end a2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("system s2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end s2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("system implementation s2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end s2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("process p2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end p2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("process implementation p2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end p2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("thread group tg2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end tg2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("thread group implementation tg2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end tg2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("thread t2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end t2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("thread implementation t2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end t2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("subprogram sub2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end sub2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("subprogram implementation sub2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end sub2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("subprogram group subg2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end subg2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("subprogram group implementation subg2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end subg2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("processor proc2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end proc2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("processor implementation proc2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end proc2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("virtual processor vp2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end vp2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("virtual processor implementation vp2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end vp2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("device dev2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end dev2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("device implementation dev2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end dev2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("memory m2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end m2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("memory implementation m2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end m2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("bus b2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end b2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("bus implementation b2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end b2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("virtual bus vb2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end vb2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("virtual bus implementation vb2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end vb2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("data d2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end d2;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("data implementation d2.i");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end d2.i;");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("feature group fgt2");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("end fgt2;");
    _builder_1.newLine();
    _builder_1.append("end pack2;");
    _builder_1.newLine();
    Pair<String, String> _mappedTo_1 = Pair.<String, String>of("pack2.aadl", _builder_1.toString());
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("package pack3");
    _builder_2.newLine();
    _builder_2.append("public");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("abstract a3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end a3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("abstract implementation a3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end a3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("system s3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end s3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("system implementation s3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end s3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("process p3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end p3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("process implementation p3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end p3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("thread group tg3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end tg3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("thread group implementation tg3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end tg3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("thread t3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end t3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("thread implementation t3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end t3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("subprogram sub3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end sub3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("subprogram implementation sub3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end sub3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("subprogram group subg3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end subg3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("subprogram group implementation subg3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end subg3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("processor proc3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end proc3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("processor implementation proc3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end proc3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("virtual processor vp3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end vp3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("virtual processor implementation vp3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end vp3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("device dev3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end dev3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("device implementation dev3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end dev3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("memory m3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end m3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("memory implementation m3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end m3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("bus b3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end b3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("bus implementation b3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end b3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("virtual bus vb3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end vb3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("virtual bus implementation vb3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end vb3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("data d3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end d3;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("data implementation d3.i");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end d3.i;");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("feature group fgt3");
    _builder_2.newLine();
    _builder_2.append("  ");
    _builder_2.append("end fgt3;");
    _builder_2.newLine();
    _builder_2.append("end pack3;");
    _builder_2.newLine();
    Pair<String, String> _mappedTo_2 = Pair.<String, String>of("pack3.aadl", _builder_2.toString());
    StringConcatenation _builder_3 = new StringConcatenation();
    _builder_3.append("package pack4");
    _builder_3.newLine();
    _builder_3.append("public");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("abstract a4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end a4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("abstract implementation a4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end a4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("system s4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end s4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("system implementation s4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end s4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("process p4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end p4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("process implementation p4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end p4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("thread group tg4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end tg4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("thread group implementation tg4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end tg4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("thread t4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end t4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("thread implementation t4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end t4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("subprogram sub4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end sub4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("subprogram implementation sub4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end sub4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("subprogram group subg4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end subg4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("subprogram group implementation subg4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end subg4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("processor proc4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end proc4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("processor implementation proc4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end proc4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("virtual processor vp4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end vp4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("virtual processor implementation vp4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end vp4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("device dev4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end dev4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("device implementation dev4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end dev4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("memory m4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end m4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("memory implementation m4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end m4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("bus b4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end b4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("bus implementation b4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end b4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("virtual bus vb4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end vb4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("virtual bus implementation vb4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end vb4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("data d4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end d4;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("data implementation d4.i");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end d4.i;");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("feature group fgt4");
    _builder_3.newLine();
    _builder_3.append("  ");
    _builder_3.append("end fgt4;");
    _builder_3.newLine();
    _builder_3.append("end pack4;");
    _builder_3.newLine();
    Pair<String, String> _mappedTo_3 = Pair.<String, String>of("pack4.aadl", _builder_3.toString());
    StringConcatenation _builder_4 = new StringConcatenation();
    _builder_4.append("package pack5");
    _builder_4.newLine();
    _builder_4.append("public");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("abstract a5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end a5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("abstract implementation a5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end a5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("system s5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end s5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("system implementation s5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end s5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("process p5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end p5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("process implementation p5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end p5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("thread group tg5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end tg5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("thread group implementation tg5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end tg5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("thread t5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end t5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("thread implementation t5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end t5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("subprogram sub5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end sub5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("subprogram implementation sub5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end sub5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("subprogram group subg5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end subg5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("subprogram group implementation subg5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end subg5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("processor proc5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end proc5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("processor implementation proc5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end proc5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("virtual processor vp5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end vp5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("virtual processor implementation vp5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end vp5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("device dev5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end dev5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("device implementation dev5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end dev5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("memory m5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end m5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("memory implementation m5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end m5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("bus b5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end b5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("bus implementation b5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end b5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("virtual bus vb5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end vb5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("virtual bus implementation vb5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end vb5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("data d5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end d5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("data implementation d5.i");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end d5.i;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("feature group fgt5");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end fgt5;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("abstract a6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end a6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("system s6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end s6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("process p6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end p6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("thread group tg6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end tg6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("thread t6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end t6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("subprogram sub6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end sub6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("subprogram group subg6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end subg6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("processor proc6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end proc6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("virtual processor vp6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end vp6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("device dev6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end dev6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("memory m6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end m6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("bus b6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end b6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("virtual bus vb6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end vb6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("data d6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end d6;");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("feature group fgt6");
    _builder_4.newLine();
    _builder_4.append("  ");
    _builder_4.append("end fgt6;");
    _builder_4.newLine();
    _builder_4.append("end pack5;");
    _builder_4.newLine();
    Pair<String, String> _mappedTo_4 = Pair.<String, String>of("pack5.aadl", _builder_4.toString());
    this.createFiles(_mappedTo, _mappedTo_1, _mappedTo_2, _mappedTo_3, _mappedTo_4);
    this.suppressSerialization();
    FluentIssueCollection _testFile = this.testFile("pack1.aadl");
    Resource _resource = _testFile.getResource();
    EList<EObject> _contents = _resource.getContents();
    EObject _head = IterableExtensions.<EObject>head(_contents);
    final AadlPackage pack1 = ((AadlPackage) _head);
    this.assertAllCrossReferencesResolvable(pack1);
    final Procedure1<AadlPackage> _function = new Procedure1<AadlPackage>() {
      public void apply(final AadlPackage it) {
        String _name = it.getName();
        Assert.assertEquals("pack1", _name);
        PublicPackageSection _publicSection = it.getPublicSection();
        EList<Classifier> _ownedClassifiers = _publicSection.getOwnedClassifiers();
        Classifier _get = _ownedClassifiers.get(0);
        final Procedure1<AbstractType> _function = new Procedure1<AbstractType>() {
          public void apply(final AbstractType it) {
            String _name = it.getName();
            Assert.assertEquals("container", _name);
            EList<DataPort> _ownedDataPorts = it.getOwnedDataPorts();
            DataPort _head = IterableExtensions.<DataPort>head(_ownedDataPorts);
            final Procedure1<DataPort> _function = new Procedure1<DataPort>() {
              public void apply(final DataPort it) {
                String _name = it.getName();
                Assert.assertEquals("dport1", _name);
                EReference _dataPort_DataFeatureClassifier = Aadl2Package.eINSTANCE.getDataPort_DataFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _dataPort_DataFeatureClassifier, (((((("aproto1, dproto1, a5, d5, ra, rd, a3, a3.i, d3, d3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.d4, renamed_package.d4.i, container, container.i, a1, a1.i, d1, d1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.d1, pack1.d1.i, pack3.a3, pack3.a3.i, pack3.d3, pack3.d3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.d2, pack2.d2.i, pack5.a5, pack5.a5.i, pack5.d5, pack5.d5.i, pack5.a6, pack5.d6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.d4, pack4.d4.i, Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, ") + 
                  "Base_Types.Integer_64, Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, ") + 
                  "Base_Types.Natural, Base_Types.Float, Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String"));
              }
            };
            ObjectExtensions.<DataPort>operator_doubleArrow(_head, _function);
            EList<EventDataPort> _ownedEventDataPorts = it.getOwnedEventDataPorts();
            EventDataPort _head_1 = IterableExtensions.<EventDataPort>head(_ownedEventDataPorts);
            final Procedure1<EventDataPort> _function_1 = new Procedure1<EventDataPort>() {
              public void apply(final EventDataPort it) {
                String _name = it.getName();
                Assert.assertEquals("edport1", _name);
                EReference _eventDataPort_DataFeatureClassifier = Aadl2Package.eINSTANCE.getEventDataPort_DataFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _eventDataPort_DataFeatureClassifier, (((((("aproto1, dproto1, a5, d5, ra, rd, a3, a3.i, d3, d3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.d4, renamed_package.d4.i, container, container.i, a1, a1.i, d1, d1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.d1, pack1.d1.i, pack3.a3, pack3.a3.i, pack3.d3, pack3.d3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.d2, pack2.d2.i, pack5.a5, pack5.a5.i, pack5.d5, pack5.d5.i, pack5.a6, pack5.d6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.d4, pack4.d4.i, Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, ") + 
                  "Base_Types.Integer_64, Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, ") + 
                  "Base_Types.Natural, Base_Types.Float, Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String"));
              }
            };
            ObjectExtensions.<EventDataPort>operator_doubleArrow(_head_1, _function_1);
            EList<FeatureGroup> _ownedFeatureGroups = it.getOwnedFeatureGroups();
            FeatureGroup _head_2 = IterableExtensions.<FeatureGroup>head(_ownedFeatureGroups);
            final Procedure1<FeatureGroup> _function_2 = new Procedure1<FeatureGroup>() {
              public void apply(final FeatureGroup it) {
                String _name = it.getName();
                Assert.assertEquals("fg1", _name);
                EReference _featureGroup_FeatureType = Aadl2Package.eINSTANCE.getFeatureGroup_FeatureType();
                Aadl2ScopeProviderTest.this.assertScope(it, _featureGroup_FeatureType, ("fgproto1, fgt5, rfgt, fgt3, renamed_package.fgt4, fgtcontainer, fgt1, " + 
                  "pack1.fgtcontainer, pack1.fgt1, pack3.fgt3, pack2.fgt2, pack5.fgt5, pack5.fgt6, pack4.fgt4"));
              }
            };
            ObjectExtensions.<FeatureGroup>operator_doubleArrow(_head_2, _function_2);
            EList<SubprogramAccess> _ownedSubprogramAccesses = it.getOwnedSubprogramAccesses();
            SubprogramAccess _head_3 = IterableExtensions.<SubprogramAccess>head(_ownedSubprogramAccesses);
            final Procedure1<SubprogramAccess> _function_3 = new Procedure1<SubprogramAccess>() {
              public void apply(final SubprogramAccess it) {
                String _name = it.getName();
                Assert.assertEquals("suba1", _name);
                EReference _subprogramAccess_SubprogramFeatureClassifier = Aadl2Package.eINSTANCE.getSubprogramAccess_SubprogramFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _subprogramAccess_SubprogramFeatureClassifier, (((("aproto1, subproto1, a5, sub5, ra, rsub, a3, a3.i, " + 
                  "sub3, sub3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.sub4, renamed_package.sub4.i, container, container.i, ") + 
                  "subcontainer, a1, a1.i, sub1, sub1.i, pack1.container, pack1.container.i, pack1.subcontainer, pack1.a1, pack1.a1.i, pack1.sub1, ") + 
                  "pack1.sub1.i, pack3.a3, pack3.a3.i, pack3.sub3, pack3.sub3.i, pack2.a2, pack2.a2.i, pack2.sub2, pack2.sub2.i, pack5.a5, ") + 
                  "pack5.a5.i, pack5.sub5, pack5.sub5.i, pack5.a6, pack5.sub6, pack4.a4, pack4.a4.i, pack4.sub4, pack4.sub4.i"));
              }
            };
            ObjectExtensions.<SubprogramAccess>operator_doubleArrow(_head_3, _function_3);
            EList<SubprogramGroupAccess> _ownedSubprogramGroupAccesses = it.getOwnedSubprogramGroupAccesses();
            SubprogramGroupAccess _head_4 = IterableExtensions.<SubprogramGroupAccess>head(_ownedSubprogramGroupAccesses);
            final Procedure1<SubprogramGroupAccess> _function_4 = new Procedure1<SubprogramGroupAccess>() {
              public void apply(final SubprogramGroupAccess it) {
                String _name = it.getName();
                Assert.assertEquals("subga1", _name);
                EReference _subprogramGroupAccess_SubprogramGroupFeatureClassifier = Aadl2Package.eINSTANCE.getSubprogramGroupAccess_SubprogramGroupFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _subprogramGroupAccess_SubprogramGroupFeatureClassifier, (((("aproto1, subgproto1, a5, subg5, ra, rsubg, " + 
                  "a3, a3.i, subg3, subg3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.subg4, renamed_package.subg4.i, container, ") + 
                  "container.i, a1, a1.i, subg1, subg1.i, pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.subg1, pack1.subg1.i, ") + 
                  "pack3.a3, pack3.a3.i, pack3.subg3, pack3.subg3.i, pack2.a2, pack2.a2.i, pack2.subg2, pack2.subg2.i, pack5.a5, pack5.a5.i, ") + 
                  "pack5.subg5, pack5.subg5.i, pack5.a6, pack5.subg6, pack4.a4, pack4.a4.i, pack4.subg4, pack4.subg4.i"));
              }
            };
            ObjectExtensions.<SubprogramGroupAccess>operator_doubleArrow(_head_4, _function_4);
            EList<BusAccess> _ownedBusAccesses = it.getOwnedBusAccesses();
            BusAccess _head_5 = IterableExtensions.<BusAccess>head(_ownedBusAccesses);
            final Procedure1<BusAccess> _function_5 = new Procedure1<BusAccess>() {
              public void apply(final BusAccess it) {
                String _name = it.getName();
                Assert.assertEquals("ba1", _name);
                EReference _busAccess_BusFeatureClassifier = Aadl2Package.eINSTANCE.getBusAccess_BusFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _busAccess_BusFeatureClassifier, (((("aproto1, bproto1, a5, b5, ra, rb, a3, a3.i, b3, b3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.b4, renamed_package.b4.i, container, container.i, a1, a1.i, b1, b1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.b1, pack1.b1.i, pack3.a3, pack3.a3.i, pack3.b3, pack3.b3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.b2, pack2.b2.i, pack5.a5, pack5.a5.i, pack5.b5, pack5.b5.i, pack5.a6, pack5.b6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.b4, pack4.b4.i"));
              }
            };
            ObjectExtensions.<BusAccess>operator_doubleArrow(_head_5, _function_5);
            EList<DataAccess> _ownedDataAccesses = it.getOwnedDataAccesses();
            DataAccess _head_6 = IterableExtensions.<DataAccess>head(_ownedDataAccesses);
            final Procedure1<DataAccess> _function_6 = new Procedure1<DataAccess>() {
              public void apply(final DataAccess it) {
                String _name = it.getName();
                Assert.assertEquals("da1", _name);
                EReference _dataAccess_DataFeatureClassifier = Aadl2Package.eINSTANCE.getDataAccess_DataFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _dataAccess_DataFeatureClassifier, (((((("aproto1, dproto1, a5, d5, ra, rd, a3, a3.i, d3, d3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.d4, renamed_package.d4.i, container, container.i, a1, a1.i, d1, d1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.d1, pack1.d1.i, pack3.a3, pack3.a3.i, pack3.d3, pack3.d3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.d2, pack2.d2.i, pack5.a5, pack5.a5.i, pack5.d5, pack5.d5.i, pack5.a6, pack5.d6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.d4, pack4.d4.i, Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, ") + 
                  "Base_Types.Integer_64, Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, ") + 
                  "Base_Types.Natural, Base_Types.Float, Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String"));
              }
            };
            ObjectExtensions.<DataAccess>operator_doubleArrow(_head_6, _function_6);
            EList<AbstractFeature> _ownedAbstractFeatures = it.getOwnedAbstractFeatures();
            AbstractFeature _head_7 = IterableExtensions.<AbstractFeature>head(_ownedAbstractFeatures);
            final Procedure1<AbstractFeature> _function_7 = new Procedure1<AbstractFeature>() {
              public void apply(final AbstractFeature it) {
                String _name = it.getName();
                Assert.assertEquals("af1", _name);
                EReference _abstractFeature_FeaturePrototype = Aadl2Package.eINSTANCE.getAbstractFeature_FeaturePrototype();
                Aadl2ScopeProviderTest.this.assertScope(it, _abstractFeature_FeaturePrototype, "fproto1");
              }
            };
            ObjectExtensions.<AbstractFeature>operator_doubleArrow(_head_7, _function_7);
          }
        };
        ObjectExtensions.<AbstractType>operator_doubleArrow(
          ((AbstractType) _get), _function);
        PublicPackageSection _publicSection_1 = it.getPublicSection();
        EList<Classifier> _ownedClassifiers_1 = _publicSection_1.getOwnedClassifiers();
        Classifier _get_1 = _ownedClassifiers_1.get(1);
        final Procedure1<AbstractImplementation> _function_1 = new Procedure1<AbstractImplementation>() {
          public void apply(final AbstractImplementation it) {
            String _name = it.getName();
            Assert.assertEquals("container.i", _name);
            EList<AbstractSubcomponent> _ownedAbstractSubcomponents = it.getOwnedAbstractSubcomponents();
            AbstractSubcomponent _head = IterableExtensions.<AbstractSubcomponent>head(_ownedAbstractSubcomponents);
            final Procedure1<AbstractSubcomponent> _function = new Procedure1<AbstractSubcomponent>() {
              public void apply(final AbstractSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("asub", _name);
                EReference _abstractSubcomponent_AbstractSubcomponentType = Aadl2Package.eINSTANCE.getAbstractSubcomponent_AbstractSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _abstractSubcomponent_AbstractSubcomponentType, (("aproto1, a5, ra, a3, a3.i, renamed_package.a4, " + 
                  "renamed_package.a4.i, container, container.i, a1, a1.i, pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack3.a3, ") + 
                  "pack3.a3.i, pack2.a2, pack2.a2.i, pack5.a5, pack5.a5.i, pack5.a6, pack4.a4, pack4.a4.i"));
              }
            };
            ObjectExtensions.<AbstractSubcomponent>operator_doubleArrow(_head, _function);
            EList<SystemSubcomponent> _ownedSystemSubcomponents = it.getOwnedSystemSubcomponents();
            SystemSubcomponent _head_1 = IterableExtensions.<SystemSubcomponent>head(_ownedSystemSubcomponents);
            final Procedure1<SystemSubcomponent> _function_1 = new Procedure1<SystemSubcomponent>() {
              public void apply(final SystemSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("ssub", _name);
                EReference _systemSubcomponent_SystemSubcomponentType = Aadl2Package.eINSTANCE.getSystemSubcomponent_SystemSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _systemSubcomponent_SystemSubcomponentType, (((("aproto1, sproto1, a5, s5, ra, rs, a3, a3.i, s3, s3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.s4, renamed_package.s4.i, container, container.i, a1, a1.i, s1, s1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.s1, pack1.s1.i, pack3.a3, pack3.a3.i, pack3.s3, pack3.s3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.s2, pack2.s2.i, pack5.a5, pack5.a5.i, pack5.s5, pack5.s5.i, pack5.a6, pack5.s6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.s4, pack4.s4.i"));
              }
            };
            ObjectExtensions.<SystemSubcomponent>operator_doubleArrow(_head_1, _function_1);
            EList<ProcessSubcomponent> _ownedProcessSubcomponents = it.getOwnedProcessSubcomponents();
            ProcessSubcomponent _head_2 = IterableExtensions.<ProcessSubcomponent>head(_ownedProcessSubcomponents);
            final Procedure1<ProcessSubcomponent> _function_2 = new Procedure1<ProcessSubcomponent>() {
              public void apply(final ProcessSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("psub", _name);
                EReference _processSubcomponent_ProcessSubcomponentType = Aadl2Package.eINSTANCE.getProcessSubcomponent_ProcessSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _processSubcomponent_ProcessSubcomponentType, (((("aproto1, pproto1, a5, p5, ra, rp, a3, a3.i, p3, p3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.p4, renamed_package.p4.i, container, container.i, a1, a1.i, p1, p1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.p1, pack1.p1.i, pack3.a3, pack3.a3.i, pack3.p3, pack3.p3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.p2, pack2.p2.i, pack5.a5, pack5.a5.i, pack5.p5, pack5.p5.i, pack5.a6, pack5.p6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.p4, pack4.p4.i"));
              }
            };
            ObjectExtensions.<ProcessSubcomponent>operator_doubleArrow(_head_2, _function_2);
            EList<ThreadGroupSubcomponent> _ownedThreadGroupSubcomponents = it.getOwnedThreadGroupSubcomponents();
            ThreadGroupSubcomponent _head_3 = IterableExtensions.<ThreadGroupSubcomponent>head(_ownedThreadGroupSubcomponents);
            final Procedure1<ThreadGroupSubcomponent> _function_3 = new Procedure1<ThreadGroupSubcomponent>() {
              public void apply(final ThreadGroupSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("tgsub", _name);
                EReference _threadGroupSubcomponent_ThreadGroupSubcomponentType = Aadl2Package.eINSTANCE.getThreadGroupSubcomponent_ThreadGroupSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _threadGroupSubcomponent_ThreadGroupSubcomponentType, (((("aproto1, tgproto1, a5, tg5, ra, rtg, a3, " + 
                  "a3.i, tg3, tg3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.tg4, renamed_package.tg4.i, container, container.i, ") + 
                  "a1, a1.i, tg1, tg1.i, pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.tg1, pack1.tg1.i, pack3.a3, pack3.a3.i, ") + 
                  "pack3.tg3, pack3.tg3.i, pack2.a2, pack2.a2.i, pack2.tg2, pack2.tg2.i, pack5.a5, pack5.a5.i, pack5.tg5, pack5.tg5.i, pack5.a6, ") + 
                  "pack5.tg6, pack4.a4, pack4.a4.i, pack4.tg4, pack4.tg4.i"));
              }
            };
            ObjectExtensions.<ThreadGroupSubcomponent>operator_doubleArrow(_head_3, _function_3);
            EList<ThreadSubcomponent> _ownedThreadSubcomponents = it.getOwnedThreadSubcomponents();
            ThreadSubcomponent _head_4 = IterableExtensions.<ThreadSubcomponent>head(_ownedThreadSubcomponents);
            final Procedure1<ThreadSubcomponent> _function_4 = new Procedure1<ThreadSubcomponent>() {
              public void apply(final ThreadSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("tsub", _name);
                EReference _threadSubcomponent_ThreadSubcomponentType = Aadl2Package.eINSTANCE.getThreadSubcomponent_ThreadSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _threadSubcomponent_ThreadSubcomponentType, (((("aproto1, tproto1, a5, t5, ra, rt, a3, a3.i, t3, t3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.t4, renamed_package.t4.i, container, container.i, a1, a1.i, t1, t1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.t1, pack1.t1.i, pack3.a3, pack3.a3.i, pack3.t3, pack3.t3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.t2, pack2.t2.i, pack5.a5, pack5.a5.i, pack5.t5, pack5.t5.i, pack5.a6, pack5.t6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.t4, pack4.t4.i"));
              }
            };
            ObjectExtensions.<ThreadSubcomponent>operator_doubleArrow(_head_4, _function_4);
            EList<SubprogramSubcomponent> _ownedSubprogramSubcomponents = it.getOwnedSubprogramSubcomponents();
            SubprogramSubcomponent _head_5 = IterableExtensions.<SubprogramSubcomponent>head(_ownedSubprogramSubcomponents);
            final Procedure1<SubprogramSubcomponent> _function_5 = new Procedure1<SubprogramSubcomponent>() {
              public void apply(final SubprogramSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("subsub", _name);
                EReference _subprogramSubcomponent_SubprogramSubcomponentType = Aadl2Package.eINSTANCE.getSubprogramSubcomponent_SubprogramSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _subprogramSubcomponent_SubprogramSubcomponentType, (((("aproto1, subproto1, a5, sub5, ra, rsub, a3, " + 
                  "a3.i, sub3, sub3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.sub4, renamed_package.sub4.i, container, ") + 
                  "container.i, subcontainer, a1, a1.i, sub1, sub1.i, pack1.container, pack1.container.i, pack1.subcontainer, pack1.a1, pack1.a1.i, ") + 
                  "pack1.sub1, pack1.sub1.i, pack3.a3, pack3.a3.i, pack3.sub3, pack3.sub3.i, pack2.a2, pack2.a2.i, pack2.sub2, pack2.sub2.i, ") + 
                  "pack5.a5, pack5.a5.i, pack5.sub5, pack5.sub5.i, pack5.a6, pack5.sub6, pack4.a4, pack4.a4.i, pack4.sub4, pack4.sub4.i"));
              }
            };
            ObjectExtensions.<SubprogramSubcomponent>operator_doubleArrow(_head_5, _function_5);
            EList<SubprogramGroupSubcomponent> _ownedSubprogramGroupSubcomponents = it.getOwnedSubprogramGroupSubcomponents();
            SubprogramGroupSubcomponent _head_6 = IterableExtensions.<SubprogramGroupSubcomponent>head(_ownedSubprogramGroupSubcomponents);
            final Procedure1<SubprogramGroupSubcomponent> _function_6 = new Procedure1<SubprogramGroupSubcomponent>() {
              public void apply(final SubprogramGroupSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("subgsub", _name);
                EReference _subprogramGroupSubcomponent_SubprogramGroupSubcomponentType = Aadl2Package.eINSTANCE.getSubprogramGroupSubcomponent_SubprogramGroupSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _subprogramGroupSubcomponent_SubprogramGroupSubcomponentType, (((("aproto1, subgproto1, a5, subg5, ra, " + 
                  "rsubg, a3, a3.i, subg3, subg3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.subg4, renamed_package.subg4.i, ") + 
                  "container, container.i, a1, a1.i, subg1, subg1.i, pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.subg1, ") + 
                  "pack1.subg1.i, pack3.a3, pack3.a3.i, pack3.subg3, pack3.subg3.i, pack2.a2, pack2.a2.i, pack2.subg2, pack2.subg2.i, pack5.a5, ") + 
                  "pack5.a5.i, pack5.subg5, pack5.subg5.i, pack5.a6, pack5.subg6, pack4.a4, pack4.a4.i, pack4.subg4, pack4.subg4.i"));
              }
            };
            ObjectExtensions.<SubprogramGroupSubcomponent>operator_doubleArrow(_head_6, _function_6);
            EList<ProcessorSubcomponent> _ownedProcessorSubcomponents = it.getOwnedProcessorSubcomponents();
            ProcessorSubcomponent _head_7 = IterableExtensions.<ProcessorSubcomponent>head(_ownedProcessorSubcomponents);
            final Procedure1<ProcessorSubcomponent> _function_7 = new Procedure1<ProcessorSubcomponent>() {
              public void apply(final ProcessorSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("procsub", _name);
                EReference _processorSubcomponent_ProcessorSubcomponentType = Aadl2Package.eINSTANCE.getProcessorSubcomponent_ProcessorSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _processorSubcomponent_ProcessorSubcomponentType, (((("aproto1, procproto1, a5, proc5, ra, rproc, a3, " + 
                  "a3.i, proc3, proc3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.proc4, renamed_package.proc4.i, container, ") + 
                  "container.i, a1, a1.i, proc1, proc1.i, pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.proc1, pack1.proc1.i, ") + 
                  "pack3.a3, pack3.a3.i, pack3.proc3, pack3.proc3.i, pack2.a2, pack2.a2.i, pack2.proc2, pack2.proc2.i, pack5.a5, pack5.a5.i, ") + 
                  "pack5.proc5, pack5.proc5.i, pack5.a6, pack5.proc6, pack4.a4, pack4.a4.i, pack4.proc4, pack4.proc4.i"));
              }
            };
            ObjectExtensions.<ProcessorSubcomponent>operator_doubleArrow(_head_7, _function_7);
            EList<VirtualProcessorSubcomponent> _ownedVirtualProcessorSubcomponents = it.getOwnedVirtualProcessorSubcomponents();
            VirtualProcessorSubcomponent _head_8 = IterableExtensions.<VirtualProcessorSubcomponent>head(_ownedVirtualProcessorSubcomponents);
            final Procedure1<VirtualProcessorSubcomponent> _function_8 = new Procedure1<VirtualProcessorSubcomponent>() {
              public void apply(final VirtualProcessorSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("vpsub", _name);
                EReference _virtualProcessorSubcomponent_VirtualProcessorSubcomponentType = Aadl2Package.eINSTANCE.getVirtualProcessorSubcomponent_VirtualProcessorSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _virtualProcessorSubcomponent_VirtualProcessorSubcomponentType, (((("aproto1, vpproto1, a5, vp5, ra, " + 
                  "rvp, a3, a3.i, vp3, vp3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.vp4, renamed_package.vp4.i, container, ") + 
                  "container.i, a1, a1.i, vp1, vp1.i, pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.vp1, pack1.vp1.i, pack3.a3, ") + 
                  "pack3.a3.i, pack3.vp3, pack3.vp3.i, pack2.a2, pack2.a2.i, pack2.vp2, pack2.vp2.i, pack5.a5, pack5.a5.i, pack5.vp5, pack5.vp5.i, ") + 
                  "pack5.a6, pack5.vp6, pack4.a4, pack4.a4.i, pack4.vp4, pack4.vp4.i"));
              }
            };
            ObjectExtensions.<VirtualProcessorSubcomponent>operator_doubleArrow(_head_8, _function_8);
            EList<DeviceSubcomponent> _ownedDeviceSubcomponents = it.getOwnedDeviceSubcomponents();
            DeviceSubcomponent _head_9 = IterableExtensions.<DeviceSubcomponent>head(_ownedDeviceSubcomponents);
            final Procedure1<DeviceSubcomponent> _function_9 = new Procedure1<DeviceSubcomponent>() {
              public void apply(final DeviceSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("devsub", _name);
                EReference _deviceSubcomponent_DeviceSubcomponentType = Aadl2Package.eINSTANCE.getDeviceSubcomponent_DeviceSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _deviceSubcomponent_DeviceSubcomponentType, (((("aproto1, devproto1, a5, dev5, ra, rdev, a3, a3.i, dev3, " + 
                  "dev3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.dev4, renamed_package.dev4.i, container, container.i, a1, a1.i, ") + 
                  "dev1, dev1.i, pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.dev1, pack1.dev1.i, pack3.a3, pack3.a3.i, ") + 
                  "pack3.dev3, pack3.dev3.i, pack2.a2, pack2.a2.i, pack2.dev2, pack2.dev2.i, pack5.a5, pack5.a5.i, pack5.dev5, pack5.dev5.i, ") + 
                  "pack5.a6, pack5.dev6, pack4.a4, pack4.a4.i, pack4.dev4, pack4.dev4.i"));
              }
            };
            ObjectExtensions.<DeviceSubcomponent>operator_doubleArrow(_head_9, _function_9);
            EList<MemorySubcomponent> _ownedMemorySubcomponents = it.getOwnedMemorySubcomponents();
            MemorySubcomponent _head_10 = IterableExtensions.<MemorySubcomponent>head(_ownedMemorySubcomponents);
            final Procedure1<MemorySubcomponent> _function_10 = new Procedure1<MemorySubcomponent>() {
              public void apply(final MemorySubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("msub", _name);
                EReference _memorySubcomponent_MemorySubcomponentType = Aadl2Package.eINSTANCE.getMemorySubcomponent_MemorySubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _memorySubcomponent_MemorySubcomponentType, (((("aproto1, mproto1, a5, m5, ra, rm, a3, a3.i, m3, m3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.m4, renamed_package.m4.i, container, container.i, a1, a1.i, m1, m1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.m1, pack1.m1.i, pack3.a3, pack3.a3.i, pack3.m3, pack3.m3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.m2, pack2.m2.i, pack5.a5, pack5.a5.i, pack5.m5, pack5.m5.i, pack5.a6, pack5.m6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.m4, pack4.m4.i"));
              }
            };
            ObjectExtensions.<MemorySubcomponent>operator_doubleArrow(_head_10, _function_10);
            EList<BusSubcomponent> _ownedBusSubcomponents = it.getOwnedBusSubcomponents();
            BusSubcomponent _head_11 = IterableExtensions.<BusSubcomponent>head(_ownedBusSubcomponents);
            final Procedure1<BusSubcomponent> _function_11 = new Procedure1<BusSubcomponent>() {
              public void apply(final BusSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("bsub", _name);
                EReference _busSubcomponent_BusSubcomponentType = Aadl2Package.eINSTANCE.getBusSubcomponent_BusSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _busSubcomponent_BusSubcomponentType, (((("aproto1, bproto1, a5, b5, ra, rb, a3, a3.i, b3, b3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.b4, renamed_package.b4.i, container, container.i, a1, a1.i, b1, b1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.b1, pack1.b1.i, pack3.a3, pack3.a3.i, pack3.b3, pack3.b3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.b2, pack2.b2.i, pack5.a5, pack5.a5.i, pack5.b5, pack5.b5.i, pack5.a6, pack5.b6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.b4, pack4.b4.i"));
              }
            };
            ObjectExtensions.<BusSubcomponent>operator_doubleArrow(_head_11, _function_11);
            EList<VirtualBusSubcomponent> _ownedVirtualBusSubcomponents = it.getOwnedVirtualBusSubcomponents();
            VirtualBusSubcomponent _head_12 = IterableExtensions.<VirtualBusSubcomponent>head(_ownedVirtualBusSubcomponents);
            final Procedure1<VirtualBusSubcomponent> _function_12 = new Procedure1<VirtualBusSubcomponent>() {
              public void apply(final VirtualBusSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("vbsub", _name);
                EReference _virtualBusSubcomponent_VirtualBusSubcomponentType = Aadl2Package.eINSTANCE.getVirtualBusSubcomponent_VirtualBusSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _virtualBusSubcomponent_VirtualBusSubcomponentType, (((("aproto1, vbproto1, a5, vb5, ra, rvb, a3, a3.i, " + 
                  "vb3, vb3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.vb4, renamed_package.vb4.i, container, container.i, a1, ") + 
                  "a1.i, vb1, vb1.i, pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.vb1, pack1.vb1.i, pack3.a3, pack3.a3.i, ") + 
                  "pack3.vb3, pack3.vb3.i, pack2.a2, pack2.a2.i, pack2.vb2, pack2.vb2.i, pack5.a5, pack5.a5.i, pack5.vb5, pack5.vb5.i, pack5.a6, ") + 
                  "pack5.vb6, pack4.a4, pack4.a4.i, pack4.vb4, pack4.vb4.i"));
              }
            };
            ObjectExtensions.<VirtualBusSubcomponent>operator_doubleArrow(_head_12, _function_12);
            EList<DataSubcomponent> _ownedDataSubcomponents = it.getOwnedDataSubcomponents();
            DataSubcomponent _head_13 = IterableExtensions.<DataSubcomponent>head(_ownedDataSubcomponents);
            final Procedure1<DataSubcomponent> _function_13 = new Procedure1<DataSubcomponent>() {
              public void apply(final DataSubcomponent it) {
                String _name = it.getName();
                Assert.assertEquals("dsub", _name);
                EReference _dataSubcomponent_DataSubcomponentType = Aadl2Package.eINSTANCE.getDataSubcomponent_DataSubcomponentType();
                Aadl2ScopeProviderTest.this.assertScope(it, _dataSubcomponent_DataSubcomponentType, (((((("aproto1, dproto1, a5, d5, ra, rd, a3, a3.i, d3, d3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.d4, renamed_package.d4.i, container, container.i, a1, a1.i, d1, d1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.d1, pack1.d1.i, pack3.a3, pack3.a3.i, pack3.d3, pack3.d3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.d2, pack2.d2.i, pack5.a5, pack5.a5.i, pack5.d5, pack5.d5.i, pack5.a6, pack5.d6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.d4, pack4.d4.i, Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, ") + 
                  "Base_Types.Integer_64, Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, ") + 
                  "Base_Types.Natural, Base_Types.Float, Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String"));
              }
            };
            ObjectExtensions.<DataSubcomponent>operator_doubleArrow(_head_13, _function_13);
          }
        };
        ObjectExtensions.<AbstractImplementation>operator_doubleArrow(
          ((AbstractImplementation) _get_1), _function_1);
        PublicPackageSection _publicSection_2 = it.getPublicSection();
        EList<Classifier> _ownedClassifiers_2 = _publicSection_2.getOwnedClassifiers();
        Classifier _get_2 = _ownedClassifiers_2.get(2);
        final Procedure1<SubprogramType> _function_2 = new Procedure1<SubprogramType>() {
          public void apply(final SubprogramType it) {
            String _name = it.getName();
            Assert.assertEquals("subcontainer", _name);
            EList<Parameter> _ownedParameters = it.getOwnedParameters();
            Parameter _head = IterableExtensions.<Parameter>head(_ownedParameters);
            final Procedure1<Parameter> _function = new Procedure1<Parameter>() {
              public void apply(final Parameter it) {
                String _name = it.getName();
                Assert.assertEquals("param1", _name);
                EReference _parameter_DataFeatureClassifier = Aadl2Package.eINSTANCE.getParameter_DataFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _parameter_DataFeatureClassifier, (((((("aproto2, dproto2, a5, d5, ra, rd, a3, a3.i, d3, d3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.d4, renamed_package.d4.i, container, container.i, a1, a1.i, d1, d1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.d1, pack1.d1.i, pack3.a3, pack3.a3.i, pack3.d3, pack3.d3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.d2, pack2.d2.i, pack5.a5, pack5.a5.i, pack5.d5, pack5.d5.i, pack5.a6, pack5.d6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.d4, pack4.d4.i, Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, ") + 
                  "Base_Types.Integer_64, Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, ") + 
                  "Base_Types.Natural, Base_Types.Float, Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String"));
              }
            };
            ObjectExtensions.<Parameter>operator_doubleArrow(_head, _function);
          }
        };
        ObjectExtensions.<SubprogramType>operator_doubleArrow(
          ((SubprogramType) _get_2), _function_2);
        PublicPackageSection _publicSection_3 = it.getPublicSection();
        EList<Classifier> _ownedClassifiers_3 = _publicSection_3.getOwnedClassifiers();
        Classifier _get_3 = _ownedClassifiers_3.get(3);
        final Procedure1<FeatureGroupType> _function_3 = new Procedure1<FeatureGroupType>() {
          public void apply(final FeatureGroupType it) {
            String _name = it.getName();
            Assert.assertEquals("fgtcontainer", _name);
            EList<DataPort> _ownedDataPorts = it.getOwnedDataPorts();
            DataPort _head = IterableExtensions.<DataPort>head(_ownedDataPorts);
            final Procedure1<DataPort> _function = new Procedure1<DataPort>() {
              public void apply(final DataPort it) {
                String _name = it.getName();
                Assert.assertEquals("dport2", _name);
                EReference _dataPort_DataFeatureClassifier = Aadl2Package.eINSTANCE.getDataPort_DataFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _dataPort_DataFeatureClassifier, (((((("aproto3, dproto3, a5, d5, ra, rd, a3, a3.i, d3, d3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.d4, renamed_package.d4.i, container, container.i, a1, a1.i, d1, d1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.d1, pack1.d1.i, pack3.a3, pack3.a3.i, pack3.d3, pack3.d3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.d2, pack2.d2.i, pack5.a5, pack5.a5.i, pack5.d5, pack5.d5.i, pack5.a6, pack5.d6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.d4, pack4.d4.i, Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, ") + 
                  "Base_Types.Integer_64, Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, ") + 
                  "Base_Types.Natural, Base_Types.Float, Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String"));
              }
            };
            ObjectExtensions.<DataPort>operator_doubleArrow(_head, _function);
            EList<EventDataPort> _ownedEventDataPorts = it.getOwnedEventDataPorts();
            EventDataPort _head_1 = IterableExtensions.<EventDataPort>head(_ownedEventDataPorts);
            final Procedure1<EventDataPort> _function_1 = new Procedure1<EventDataPort>() {
              public void apply(final EventDataPort it) {
                String _name = it.getName();
                Assert.assertEquals("edport2", _name);
                EReference _eventDataPort_DataFeatureClassifier = Aadl2Package.eINSTANCE.getEventDataPort_DataFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _eventDataPort_DataFeatureClassifier, (((((("aproto3, dproto3, a5, d5, ra, rd, a3, a3.i, d3, d3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.d4, renamed_package.d4.i, container, container.i, a1, a1.i, d1, d1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.d1, pack1.d1.i, pack3.a3, pack3.a3.i, pack3.d3, pack3.d3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.d2, pack2.d2.i, pack5.a5, pack5.a5.i, pack5.d5, pack5.d5.i, pack5.a6, pack5.d6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.d4, pack4.d4.i, Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, ") + 
                  "Base_Types.Integer_64, Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, ") + 
                  "Base_Types.Natural, Base_Types.Float, Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String"));
              }
            };
            ObjectExtensions.<EventDataPort>operator_doubleArrow(_head_1, _function_1);
            EList<FeatureGroup> _ownedFeatureGroups = it.getOwnedFeatureGroups();
            FeatureGroup _head_2 = IterableExtensions.<FeatureGroup>head(_ownedFeatureGroups);
            final Procedure1<FeatureGroup> _function_2 = new Procedure1<FeatureGroup>() {
              public void apply(final FeatureGroup it) {
                String _name = it.getName();
                Assert.assertEquals("fg2", _name);
                EReference _featureGroup_FeatureType = Aadl2Package.eINSTANCE.getFeatureGroup_FeatureType();
                Aadl2ScopeProviderTest.this.assertScope(it, _featureGroup_FeatureType, ("fgproto3, fgt5, rfgt, fgt3, renamed_package.fgt4, fgtcontainer, fgt1, " + 
                  "pack1.fgtcontainer, pack1.fgt1, pack3.fgt3, pack2.fgt2, pack5.fgt5, pack5.fgt6, pack4.fgt4"));
              }
            };
            ObjectExtensions.<FeatureGroup>operator_doubleArrow(_head_2, _function_2);
            EList<Parameter> _ownedParameters = it.getOwnedParameters();
            Parameter _head_3 = IterableExtensions.<Parameter>head(_ownedParameters);
            final Procedure1<Parameter> _function_3 = new Procedure1<Parameter>() {
              public void apply(final Parameter it) {
                String _name = it.getName();
                Assert.assertEquals("param2", _name);
                EReference _parameter_DataFeatureClassifier = Aadl2Package.eINSTANCE.getParameter_DataFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _parameter_DataFeatureClassifier, (((((("aproto3, dproto3, a5, d5, ra, rd, a3, a3.i, d3, d3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.d4, renamed_package.d4.i, container, container.i, a1, a1.i, d1, d1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.d1, pack1.d1.i, pack3.a3, pack3.a3.i, pack3.d3, pack3.d3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.d2, pack2.d2.i, pack5.a5, pack5.a5.i, pack5.d5, pack5.d5.i, pack5.a6, pack5.d6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.d4, pack4.d4.i, Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, ") + 
                  "Base_Types.Integer_64, Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, ") + 
                  "Base_Types.Natural, Base_Types.Float, Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String"));
              }
            };
            ObjectExtensions.<Parameter>operator_doubleArrow(_head_3, _function_3);
            EList<SubprogramAccess> _ownedSubprogramAccesses = it.getOwnedSubprogramAccesses();
            SubprogramAccess _head_4 = IterableExtensions.<SubprogramAccess>head(_ownedSubprogramAccesses);
            final Procedure1<SubprogramAccess> _function_4 = new Procedure1<SubprogramAccess>() {
              public void apply(final SubprogramAccess it) {
                String _name = it.getName();
                Assert.assertEquals("suba2", _name);
                EReference _subprogramAccess_SubprogramFeatureClassifier = Aadl2Package.eINSTANCE.getSubprogramAccess_SubprogramFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _subprogramAccess_SubprogramFeatureClassifier, (((("aproto3, subproto3, a5, sub5, ra, rsub, a3, a3.i, " + 
                  "sub3, sub3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.sub4, renamed_package.sub4.i, container, container.i, ") + 
                  "subcontainer, a1, a1.i, sub1, sub1.i, pack1.container, pack1.container.i, pack1.subcontainer, pack1.a1, pack1.a1.i, pack1.sub1, ") + 
                  "pack1.sub1.i, pack3.a3, pack3.a3.i, pack3.sub3, pack3.sub3.i, pack2.a2, pack2.a2.i, pack2.sub2, pack2.sub2.i, pack5.a5, ") + 
                  "pack5.a5.i, pack5.sub5, pack5.sub5.i, pack5.a6, pack5.sub6, pack4.a4, pack4.a4.i, pack4.sub4, pack4.sub4.i"));
              }
            };
            ObjectExtensions.<SubprogramAccess>operator_doubleArrow(_head_4, _function_4);
            EList<SubprogramGroupAccess> _ownedSubprogramGroupAccesses = it.getOwnedSubprogramGroupAccesses();
            SubprogramGroupAccess _head_5 = IterableExtensions.<SubprogramGroupAccess>head(_ownedSubprogramGroupAccesses);
            final Procedure1<SubprogramGroupAccess> _function_5 = new Procedure1<SubprogramGroupAccess>() {
              public void apply(final SubprogramGroupAccess it) {
                String _name = it.getName();
                Assert.assertEquals("subga2", _name);
                EReference _subprogramGroupAccess_SubprogramGroupFeatureClassifier = Aadl2Package.eINSTANCE.getSubprogramGroupAccess_SubprogramGroupFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _subprogramGroupAccess_SubprogramGroupFeatureClassifier, (((("aproto3, subgproto3, a5, subg5, ra, rsubg, " + 
                  "a3, a3.i, subg3, subg3.i, renamed_package.a4, renamed_package.a4.i, renamed_package.subg4, renamed_package.subg4.i, container, ") + 
                  "container.i, a1, a1.i, subg1, subg1.i, pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.subg1, pack1.subg1.i, ") + 
                  "pack3.a3, pack3.a3.i, pack3.subg3, pack3.subg3.i, pack2.a2, pack2.a2.i, pack2.subg2, pack2.subg2.i, pack5.a5, pack5.a5.i, ") + 
                  "pack5.subg5, pack5.subg5.i, pack5.a6, pack5.subg6, pack4.a4, pack4.a4.i, pack4.subg4, pack4.subg4.i"));
              }
            };
            ObjectExtensions.<SubprogramGroupAccess>operator_doubleArrow(_head_5, _function_5);
            EList<BusAccess> _ownedBusAccesses = it.getOwnedBusAccesses();
            BusAccess _head_6 = IterableExtensions.<BusAccess>head(_ownedBusAccesses);
            final Procedure1<BusAccess> _function_6 = new Procedure1<BusAccess>() {
              public void apply(final BusAccess it) {
                String _name = it.getName();
                Assert.assertEquals("ba2", _name);
                EReference _busAccess_BusFeatureClassifier = Aadl2Package.eINSTANCE.getBusAccess_BusFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _busAccess_BusFeatureClassifier, (((("aproto3, bproto3, a5, b5, ra, rb, a3, a3.i, b3, b3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.b4, renamed_package.b4.i, container, container.i, a1, a1.i, b1, b1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.b1, pack1.b1.i, pack3.a3, pack3.a3.i, pack3.b3, pack3.b3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.b2, pack2.b2.i, pack5.a5, pack5.a5.i, pack5.b5, pack5.b5.i, pack5.a6, pack5.b6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.b4, pack4.b4.i"));
              }
            };
            ObjectExtensions.<BusAccess>operator_doubleArrow(_head_6, _function_6);
            EList<DataAccess> _ownedDataAccesses = it.getOwnedDataAccesses();
            DataAccess _head_7 = IterableExtensions.<DataAccess>head(_ownedDataAccesses);
            final Procedure1<DataAccess> _function_7 = new Procedure1<DataAccess>() {
              public void apply(final DataAccess it) {
                String _name = it.getName();
                Assert.assertEquals("da2", _name);
                EReference _dataAccess_DataFeatureClassifier = Aadl2Package.eINSTANCE.getDataAccess_DataFeatureClassifier();
                Aadl2ScopeProviderTest.this.assertScope(it, _dataAccess_DataFeatureClassifier, (((((("aproto3, dproto3, a5, d5, ra, rd, a3, a3.i, d3, d3.i, " + 
                  "renamed_package.a4, renamed_package.a4.i, renamed_package.d4, renamed_package.d4.i, container, container.i, a1, a1.i, d1, d1.i, ") + 
                  "pack1.container, pack1.container.i, pack1.a1, pack1.a1.i, pack1.d1, pack1.d1.i, pack3.a3, pack3.a3.i, pack3.d3, pack3.d3.i, ") + 
                  "pack2.a2, pack2.a2.i, pack2.d2, pack2.d2.i, pack5.a5, pack5.a5.i, pack5.d5, pack5.d5.i, pack5.a6, pack5.d6, pack4.a4, pack4.a4.i, ") + 
                  "pack4.d4, pack4.d4.i, Base_Types.Boolean, Base_Types.Integer, Base_Types.Integer_8, Base_Types.Integer_16, Base_Types.Integer_32, ") + 
                  "Base_Types.Integer_64, Base_Types.Unsigned_8, Base_Types.Unsigned_16, Base_Types.Unsigned_32, Base_Types.Unsigned_64, ") + 
                  "Base_Types.Natural, Base_Types.Float, Base_Types.Float_32, Base_Types.Float_64, Base_Types.Character, Base_Types.String"));
              }
            };
            ObjectExtensions.<DataAccess>operator_doubleArrow(_head_7, _function_7);
            EList<AbstractFeature> _ownedAbstractFeatures = it.getOwnedAbstractFeatures();
            AbstractFeature _head_8 = IterableExtensions.<AbstractFeature>head(_ownedAbstractFeatures);
            final Procedure1<AbstractFeature> _function_8 = new Procedure1<AbstractFeature>() {
              public void apply(final AbstractFeature it) {
                String _name = it.getName();
                Assert.assertEquals("af2", _name);
                EReference _abstractFeature_FeaturePrototype = Aadl2Package.eINSTANCE.getAbstractFeature_FeaturePrototype();
                Aadl2ScopeProviderTest.this.assertScope(it, _abstractFeature_FeaturePrototype, "fproto3");
              }
            };
            ObjectExtensions.<AbstractFeature>operator_doubleArrow(_head_8, _function_8);
          }
        };
        ObjectExtensions.<FeatureGroupType>operator_doubleArrow(
          ((FeatureGroupType) _get_3), _function_3);
      }
    };
    ObjectExtensions.<AadlPackage>operator_doubleArrow(pack1, _function);
  }
  
  /**
   * Tests the reference ArraySize_SizeProperty used in the parser rule ArraySize.
   * The scope for this rule is automatically provided, so there is no scoping method to test here.
   */
  @Test
  public void testArraySizeProperty() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package pack");
    _builder.newLine();
    _builder.append("public");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("with ps;");
    _builder.newLine();
    _builder.append("  ");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("abstract a");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("features");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("af: feature[ps::def];");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("properties");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ps::def => 4;");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("end a;");
    _builder.newLine();
    _builder.append("end pack;");
    _builder.newLine();
    Pair<String, String> _mappedTo = Pair.<String, String>of("pack.aadl", _builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("property set ps is");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("def: aadlinteger applies to (all);");
    _builder_1.newLine();
    _builder_1.append("  ");
    _builder_1.append("const: constant aadlinteger => 10;");
    _builder_1.newLine();
    _builder_1.append("end ps;");
    _builder_1.newLine();
    Pair<String, String> _mappedTo_1 = Pair.<String, String>of("ps.aadl", _builder_1.toString());
    this.createFiles(_mappedTo, _mappedTo_1);
    this.suppressSerialization();
    FluentIssueCollection _testFile = this.testFile("pack.aadl");
    Resource _resource = _testFile.getResource();
    EList<EObject> _contents = _resource.getContents();
    EObject _head = IterableExtensions.<EObject>head(_contents);
    final AadlPackage pack = ((AadlPackage) _head);
    this.assertAllCrossReferencesResolvable(pack);
    final Procedure1<AadlPackage> _function = new Procedure1<AadlPackage>() {
      public void apply(final AadlPackage it) {
        String _name = it.getName();
        Assert.assertEquals("pack", _name);
        PublicPackageSection _publicSection = it.getPublicSection();
        EList<Classifier> _ownedClassifiers = _publicSection.getOwnedClassifiers();
        Classifier _head = IterableExtensions.<Classifier>head(_ownedClassifiers);
        final Procedure1<ComponentType> _function = new Procedure1<ComponentType>() {
          public void apply(final ComponentType it) {
            String _name = it.getName();
            Assert.assertEquals("a", _name);
            EList<AbstractFeature> _ownedAbstractFeatures = it.getOwnedAbstractFeatures();
            AbstractFeature _head = IterableExtensions.<AbstractFeature>head(_ownedAbstractFeatures);
            final Procedure1<AbstractFeature> _function = new Procedure1<AbstractFeature>() {
              public void apply(final AbstractFeature it) {
                String _name = it.getName();
                Assert.assertEquals("af", _name);
                EList<ArrayDimension> _arrayDimensions = it.getArrayDimensions();
                ArrayDimension _head = IterableExtensions.<ArrayDimension>head(_arrayDimensions);
                ArraySize _size = _head.getSize();
                final Procedure1<ArraySize> _function = new Procedure1<ArraySize>() {
                  public void apply(final ArraySize it) {
                    ArraySizeProperty _sizeProperty = it.getSizeProperty();
                    String _qualifiedName = ((NamedElement) _sizeProperty).qualifiedName();
                    Assert.assertEquals("ps::def", _qualifiedName);
                    EReference _arraySize_SizeProperty = Aadl2Package.eINSTANCE.getArraySize_SizeProperty();
                    StringConcatenation _builder = new StringConcatenation();
                    _builder.append("ps.def, ps.const, ");
                    _builder.newLine();
                    _builder.append("Access_Right, Access_Time, Allowed_Message_Size, Assign_Time, Base_Address, Device_Register_Address, Read_Time, Code_Size, ");
                    _builder.newLine();
                    _builder.append("Data_Size, Heap_Size, Stack_Size, Byte_Count, Memory_Size, Word_Size, Word_Space, Write_Time, Fan_Out_Policy, Connection_Pattern, ");
                    _builder.newLine();
                    _builder.append("Connection_Set, Overflow_Handling_Protocol, Queue_Processing_Protocol, Queue_Size, Required_Connection, Timing, Transmission_Type, ");
                    _builder.newLine();
                    _builder.append("Input_Rate, Input_Time, Output_Rate, Output_Time, Subprogram_Call_Rate, Transmission_Time, Actual_Latency, Latency, Data_Rate, ");
                    _builder.newLine();
                    _builder.append("Acceptable_Array_Size, Classifier_Matching_Rule, Classifier_Substitution_Rule, Implemented_As, Prototype_Substitution_Rule, ");
                    _builder.newLine();
                    _builder.append("Allowed_Processor_Binding_Class, Allowed_Processor_Binding, Actual_Processor_Binding, Allowed_Memory_Binding_Class, ");
                    _builder.newLine();
                    _builder.append("Allowed_Memory_Binding, Actual_Memory_Binding, Allowed_Connection_Binding_Class, Allowed_Connection_Binding, ");
                    _builder.newLine();
                    _builder.append("Actual_Connection_Binding, Actual_Function_Binding, Allowed_Subprogram_Call, Actual_Subprogram_Call, ");
                    _builder.newLine();
                    _builder.append("Allowed_Subprogram_Call_Binding, Actual_Subprogram_Call_Binding, Provided_Virtual_Bus_Class, Required_Virtual_Bus_Class, ");
                    _builder.newLine();
                    _builder.append("Provided_Connection_Quality_Of_Service, Required_Connection_Quality_Of_Service, Not_Collocated, Collocated, Allowed_Connection_Type, ");
                    _builder.newLine();
                    _builder.append("Allowed_Dispatch_Protocol, Allowed_Period, Allowed_Physical_Access_Class, Allowed_Physical_Access, Memory_Protocol, ");
                    _builder.newLine();
                    _builder.append("Runtime_Protection_Support, Scheduling_Protocol, Preemptive_Scheduler, Thread_Limit, Priority_Map, Priority_Range, ");
                    _builder.newLine();
                    _builder.append("Activate_Entrypoint, Activate_Entrypoint_Call_Sequence, Activate_Entrypoint_Source_Text, Compute_Entrypoint, ");
                    _builder.newLine();
                    _builder.append("Compute_Entrypoint_Call_Sequence, Compute_Entrypoint_Source_Text, Deactivate_Entrypoint, Deactivate_Entrypoint_Call_Sequence, ");
                    _builder.newLine();
                    _builder.append("Deactivate_Entrypoint_Source_Text, Finalize_Entrypoint, Finalize_Entrypoint_Call_Sequence, Finalize_Entrypoint_Source_Text, ");
                    _builder.newLine();
                    _builder.append("Initialize_Entrypoint, Initialize_Entrypoint_Call_Sequence, Initialize_Entrypoint_Source_Text, Recover_Entrypoint, ");
                    _builder.newLine();
                    _builder.append("Recover_Entrypoint_Call_Sequence, Recover_Entrypoint_Source_Text, Source_Language, Source_Name, Source_Text, ");
                    _builder.newLine();
                    _builder.append("Supported_Source_Language, Type_Source_Name, Hardware_Description_Source_Text, Hardware_Source_Language, Device_Driver, ");
                    _builder.newLine();
                    _builder.append("Supported_Classifier_Equivalence_Matches, Supported_Classifier_Subset_Matches, Supported_Type_Conversions, ");
                    _builder.newLine();
                    _builder.append("Supported_Classifier_Complement_Matches, Max_Aadlinteger, Max_Target_Integer, Max_Base_Address, Max_Memory_Size, Max_Queue_Size, ");
                    _builder.newLine();
                    _builder.append("Max_Thread_Limit, Max_Time, Max_Urgency, Max_Byte_Count, Max_Word_Space, Max_Volume, Activate_Deadline, Activate_Execution_Time, ");
                    _builder.newLine();
                    _builder.append("Compute_Deadline, Compute_Execution_Time, Client_Subprogram_Execution_Time, Deactivate_Dealing, Deactivate_Execution_Time, Deadline, ");
                    _builder.newLine();
                    _builder.append("First_Dispatch_Time, Dispatch_Jitter, Dispatch_Offset, Execution_Time, Finalize_Deadline, Finalize_Execution_Time, ");
                    _builder.newLine();
                    _builder.append("Initialize_Deadline, Initialize_Execution_Time, Load_Deadline, Load_Time, Processor_Capacity, Period, Recover_Deadline, ");
                    _builder.newLine();
                    _builder.append("Recover_Execution_Time, Startup_Deadline, Startup_Execution_Time, Clock_Jitter, Clock_Period, Clock_Period_Range, ");
                    _builder.newLine();
                    _builder.append("Process_Swap_Execution_Time, Reference_Processor, Scheduler_Quantum, Thread_Swap_Execution_Time, Frame_Period, Slot_Time, ");
                    _builder.newLine();
                    _builder.append("Dispatch_Protocol, Dispatch_Trigger, Dispatch_Able, POSIX_Scheduling_Policy, Priority, Criticality, Time_Slot, ");
                    _builder.newLine();
                    _builder.append("Concurrency_Control_Protocol, Urgency, Dequeue_Protocol, Dequeued_Items, Mode_Transition_Response, Resumption_Policy, ");
                    _builder.newLine();
                    _builder.append("Active_Thread_Handling_Protocol, Active_Thread_Queue_Handling_Protocol, Deactivation_Policy, Runtime_Protection, ");
                    _builder.newLine();
                    _builder.append("Subprogram_Call_Type, Synchronized_Component, Memory_Properties.Access_Right, Memory_Properties.Access_Time, ");
                    _builder.newLine();
                    _builder.append("Memory_Properties.Allowed_Message_Size, Memory_Properties.Assign_Time, Memory_Properties.Base_Address, ");
                    _builder.newLine();
                    _builder.append("Memory_Properties.Device_Register_Address, Memory_Properties.Read_Time, Memory_Properties.Code_Size, Memory_Properties.Data_Size, ");
                    _builder.newLine();
                    _builder.append("Memory_Properties.Heap_Size, Memory_Properties.Stack_Size, Memory_Properties.Byte_Count, Memory_Properties.Memory_Size, ");
                    _builder.newLine();
                    _builder.append("Memory_Properties.Word_Size, Memory_Properties.Word_Space, Memory_Properties.Write_Time, Communication_Properties.Fan_Out_Policy, ");
                    _builder.newLine();
                    _builder.append("Communication_Properties.Connection_Pattern, Communication_Properties.Connection_Set, ");
                    _builder.newLine();
                    _builder.append("Communication_Properties.Overflow_Handling_Protocol, Communication_Properties.Queue_Processing_Protocol, ");
                    _builder.newLine();
                    _builder.append("Communication_Properties.Queue_Size, Communication_Properties.Required_Connection, Communication_Properties.Timing, ");
                    _builder.newLine();
                    _builder.append("Communication_Properties.Transmission_Type, Communication_Properties.Input_Rate, Communication_Properties.Input_Time, ");
                    _builder.newLine();
                    _builder.append("Communication_Properties.Output_Rate, Communication_Properties.Output_Time, Communication_Properties.Subprogram_Call_Rate, ");
                    _builder.newLine();
                    _builder.append("Communication_Properties.Transmission_Time, Communication_Properties.Actual_Latency, Communication_Properties.Latency, ");
                    _builder.newLine();
                    _builder.append("Communication_Properties.Data_Rate, Modeling_Properties.Acceptable_Array_Size, Modeling_Properties.Classifier_Matching_Rule, ");
                    _builder.newLine();
                    _builder.append("Modeling_Properties.Classifier_Substitution_Rule, Modeling_Properties.Implemented_As, ");
                    _builder.newLine();
                    _builder.append("Modeling_Properties.Prototype_Substitution_Rule, Deployment_Properties.Allowed_Processor_Binding_Class, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Allowed_Processor_Binding, Deployment_Properties.Actual_Processor_Binding, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Allowed_Memory_Binding_Class, Deployment_Properties.Allowed_Memory_Binding, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Actual_Memory_Binding, Deployment_Properties.Allowed_Connection_Binding_Class, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Allowed_Connection_Binding, Deployment_Properties.Actual_Connection_Binding, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Actual_Function_Binding, Deployment_Properties.Allowed_Subprogram_Call, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Actual_Subprogram_Call, Deployment_Properties.Allowed_Subprogram_Call_Binding, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Actual_Subprogram_Call_Binding, Deployment_Properties.Provided_Virtual_Bus_Class, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Required_Virtual_Bus_Class, Deployment_Properties.Provided_Connection_Quality_Of_Service, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Required_Connection_Quality_Of_Service, Deployment_Properties.Not_Collocated, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Collocated, Deployment_Properties.Allowed_Connection_Type, Deployment_Properties.Allowed_Dispatch_Protocol, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Allowed_Period, Deployment_Properties.Allowed_Physical_Access_Class, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Allowed_Physical_Access, Deployment_Properties.Memory_Protocol, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Runtime_Protection_Support, Deployment_Properties.Scheduling_Protocol, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Preemptive_Scheduler, Deployment_Properties.Thread_Limit, Deployment_Properties.Priority_Map, ");
                    _builder.newLine();
                    _builder.append("Deployment_Properties.Priority_Range, Programming_Properties.Activate_Entrypoint, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Activate_Entrypoint_Call_Sequence, Programming_Properties.Activate_Entrypoint_Source_Text, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Compute_Entrypoint, Programming_Properties.Compute_Entrypoint_Call_Sequence, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Compute_Entrypoint_Source_Text, Programming_Properties.Deactivate_Entrypoint, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Deactivate_Entrypoint_Call_Sequence, Programming_Properties.Deactivate_Entrypoint_Source_Text, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Finalize_Entrypoint, Programming_Properties.Finalize_Entrypoint_Call_Sequence, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Finalize_Entrypoint_Source_Text, Programming_Properties.Initialize_Entrypoint, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Initialize_Entrypoint_Call_Sequence, Programming_Properties.Initialize_Entrypoint_Source_Text, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Recover_Entrypoint, Programming_Properties.Recover_Entrypoint_Call_Sequence, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Recover_Entrypoint_Source_Text, Programming_Properties.Source_Language, Programming_Properties.Source_Name, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Source_Text, Programming_Properties.Supported_Source_Language, Programming_Properties.Type_Source_Name, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Hardware_Description_Source_Text, Programming_Properties.Hardware_Source_Language, ");
                    _builder.newLine();
                    _builder.append("Programming_Properties.Device_Driver, AADL_Project.Supported_Classifier_Equivalence_Matches, ");
                    _builder.newLine();
                    _builder.append("AADL_Project.Supported_Classifier_Subset_Matches, AADL_Project.Supported_Type_Conversions, ");
                    _builder.newLine();
                    _builder.append("AADL_Project.Supported_Classifier_Complement_Matches, AADL_Project.Max_Aadlinteger, AADL_Project.Max_Target_Integer, ");
                    _builder.newLine();
                    _builder.append("AADL_Project.Max_Base_Address, AADL_Project.Max_Memory_Size, AADL_Project.Max_Queue_Size, AADL_Project.Max_Thread_Limit, ");
                    _builder.newLine();
                    _builder.append("AADL_Project.Max_Time, AADL_Project.Max_Urgency, AADL_Project.Max_Byte_Count, AADL_Project.Max_Word_Space, AADL_Project.Max_Volume, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Activate_Deadline, Timing_Properties.Activate_Execution_Time, Timing_Properties.Compute_Deadline, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Compute_Execution_Time, Timing_Properties.Client_Subprogram_Execution_Time, Timing_Properties.Deactivate_Dealing, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Deactivate_Execution_Time, Timing_Properties.Deadline, Timing_Properties.First_Dispatch_Time, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Dispatch_Jitter, Timing_Properties.Dispatch_Offset, Timing_Properties.Execution_Time, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Finalize_Deadline, Timing_Properties.Finalize_Execution_Time, Timing_Properties.Initialize_Deadline, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Initialize_Execution_Time, Timing_Properties.Load_Deadline, Timing_Properties.Load_Time, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Processor_Capacity, Timing_Properties.Period, Timing_Properties.Recover_Deadline, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Recover_Execution_Time, Timing_Properties.Startup_Deadline, Timing_Properties.Startup_Execution_Time, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Clock_Jitter, Timing_Properties.Clock_Period, Timing_Properties.Clock_Period_Range, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Process_Swap_Execution_Time, Timing_Properties.Reference_Processor, Timing_Properties.Scheduler_Quantum, ");
                    _builder.newLine();
                    _builder.append("Timing_Properties.Thread_Swap_Execution_Time, Timing_Properties.Frame_Period, Timing_Properties.Slot_Time, ");
                    _builder.newLine();
                    _builder.append("Thread_Properties.Dispatch_Protocol, Thread_Properties.Dispatch_Trigger, Thread_Properties.Dispatch_Able, ");
                    _builder.newLine();
                    _builder.append("Thread_Properties.POSIX_Scheduling_Policy, Thread_Properties.Priority, Thread_Properties.Criticality, Thread_Properties.Time_Slot, ");
                    _builder.newLine();
                    _builder.append("Thread_Properties.Concurrency_Control_Protocol, Thread_Properties.Urgency, Thread_Properties.Dequeue_Protocol, ");
                    _builder.newLine();
                    _builder.append("Thread_Properties.Dequeued_Items, Thread_Properties.Mode_Transition_Response, Thread_Properties.Resumption_Policy, ");
                    _builder.newLine();
                    _builder.append("Thread_Properties.Active_Thread_Handling_Protocol, Thread_Properties.Active_Thread_Queue_Handling_Protocol, ");
                    _builder.newLine();
                    _builder.append("Thread_Properties.Deactivation_Policy, Thread_Properties.Runtime_Protection, Thread_Properties.Synchronized_Component, Thread_Properties.Subprogram_Call_Type");
                    _builder.newLine();
                    String _string = _builder.toString();
                    String _lineSeparator = System.lineSeparator();
                    String _replaceAll = _string.replaceAll(_lineSeparator, "");
                    Aadl2ScopeProviderTest.this.assertScope(it, _arraySize_SizeProperty, _replaceAll, Collections.<String>unmodifiableSet(Sets.<String>newHashSet("ps")));
                  }
                };
                ObjectExtensions.<ArraySize>operator_doubleArrow(_size, _function);
              }
            };
            ObjectExtensions.<AbstractFeature>operator_doubleArrow(_head, _function);
          }
        };
        ObjectExtensions.<ComponentType>operator_doubleArrow(
          ((ComponentType) _head), _function);
      }
    };
    ObjectExtensions.<AadlPackage>operator_doubleArrow(pack, _function);
  }
  
  private void assertScope(final EObject context, final EReference reference, final CharSequence expected) {
    this._validationTestHelper.assertNoErrors(context);
    String _string = expected.toString();
    String _replaceAll = _string.replaceAll(" ", "");
    String[] _split = _replaceAll.split(",");
    final Function1<String, Boolean> _function = new Function1<String, Boolean>() {
      public Boolean apply(final String it) {
        boolean _isEmpty = it.isEmpty();
        return Boolean.valueOf((!_isEmpty));
      }
    };
    Iterable<String> _filter = IterableExtensions.<String>filter(((Iterable<String>)Conversions.doWrapArray(_split)), _function);
    List<String> _sort = IterableExtensions.<String>sort(_filter);
    String _join = IterableExtensions.join(_sort, ", ");
    IScope _scope = this._iScopeProvider.getScope(context, reference);
    Iterable<IEObjectDescription> _allElements = _scope.getAllElements();
    final Function1<IEObjectDescription, String> _function_1 = new Function1<IEObjectDescription, String>() {
      public String apply(final IEObjectDescription it) {
        QualifiedName _name = it.getName();
        return _name.toString();
      }
    };
    Iterable<String> _map = IterableExtensions.<IEObjectDescription, String>map(_allElements, _function_1);
    List<String> _sort_1 = IterableExtensions.<String>sort(_map);
    String _join_1 = IterableExtensions.join(_sort_1, ", ");
    Assert.assertEquals(_join, _join_1);
  }
  
  private void assertScope(final EObject context, final EReference reference, final CharSequence expected, final Set<String> validPropertySets) {
    this._validationTestHelper.assertNoErrors(context);
    String _string = expected.toString();
    String _replaceAll = _string.replaceAll(" ", "");
    String[] _split = _replaceAll.split(",");
    final Function1<String, Boolean> _function = new Function1<String, Boolean>() {
      public Boolean apply(final String it) {
        boolean _isEmpty = it.isEmpty();
        return Boolean.valueOf((!_isEmpty));
      }
    };
    Iterable<String> _filter = IterableExtensions.<String>filter(((Iterable<String>)Conversions.doWrapArray(_split)), _function);
    List<String> _sort = IterableExtensions.<String>sort(_filter);
    String _join = IterableExtensions.join(_sort, ", ");
    IScope _scope = this._iScopeProvider.getScope(context, reference);
    Iterable<IEObjectDescription> _allElements = _scope.getAllElements();
    final Function1<IEObjectDescription, String> _function_1 = new Function1<IEObjectDescription, String>() {
      public String apply(final IEObjectDescription it) {
        QualifiedName _name = it.getName();
        return _name.toString();
      }
    };
    Iterable<String> _map = IterableExtensions.<IEObjectDescription, String>map(_allElements, _function_1);
    final Function1<String, Boolean> _function_2 = new Function1<String, Boolean>() {
      public Boolean apply(final String it) {
        boolean _xblockexpression = false;
        {
          final int separatorIndex = it.indexOf(".");
          boolean _xifexpression = false;
          if ((separatorIndex != (-1))) {
            boolean _xblockexpression_1 = false;
            {
              final String propertySetName = it.substring(0, separatorIndex);
              boolean _or = false;
              final Function1<String, Boolean> _function = new Function1<String, Boolean>() {
                public Boolean apply(final String it) {
                  return Boolean.valueOf(it.equalsIgnoreCase(propertySetName));
                }
              };
              boolean _exists = IterableExtensions.<String>exists(validPropertySets, _function);
              if (_exists) {
                _or = true;
              } else {
                boolean _isPredeclaredPropertySet = AadlUtil.isPredeclaredPropertySet(propertySetName);
                _or = _isPredeclaredPropertySet;
              }
              _xblockexpression_1 = _or;
            }
            _xifexpression = _xblockexpression_1;
          } else {
            _xifexpression = true;
          }
          _xblockexpression = _xifexpression;
        }
        return Boolean.valueOf(_xblockexpression);
      }
    };
    Iterable<String> _filter_1 = IterableExtensions.<String>filter(_map, _function_2);
    List<String> _sort_1 = IterableExtensions.<String>sort(_filter_1);
    String _join_1 = IterableExtensions.join(_sort_1, ", ");
    Assert.assertEquals(_join, _join_1);
  }
}