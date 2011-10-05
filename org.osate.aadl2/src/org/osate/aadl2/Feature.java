/**
 * <copyright>
 * Copyright  2008 by Carnegie Mellon University, all rights reserved.
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
 * Carnegie Mellon University Software Engineering Institute authored documents are sponsored by the U.S. Department
 * of Defense under Contract F19628-00-C-0003. Carnegie Mellon University retains copyrights in all material produced
 * under this contract. The U.S. Government retains a non-exclusive, royalty-free license to publish or reproduce these
 * documents, or allow others to do so, for U.S. Government purposes only pursuant to the copyright license
 * under the contract clause at 252.227.7013.
 * </copyright>
 *
 * $Id: Feature.java,v 1.17 2009-06-03 17:46:28 lwrage Exp $
 */
package org.osate.aadl2;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.osate.aadl2.Feature#getPrototype <em>Prototype</em>}</li>
 *   <li>{@link org.osate.aadl2.Feature#getRefined <em>Refined</em>}</li>
 *   <li>{@link org.osate.aadl2.Feature#getClassifier <em>Classifier</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.osate.aadl2.Aadl2Package#getFeature()
 * @model abstract="true"
 * @generated
 */
public interface Feature extends StructuralFeature, FeatureConnectionEnd, ArrayableElement {
	/**
	 * Returns the value of the '<em><b>Prototype</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * <ul>
	 *   <li>'{@link org.osate.aadl2.Feature#getFeatureClassifier() <em>Feature Classifier</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prototype</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prototype</em>' reference.
	 * @see org.osate.aadl2.Aadl2Package#getFeature_Prototype()
	 * @model transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	Prototype getPrototype();

	/**
	 * Returns the value of the '<em><b>Feature Classifier</b></em>' reference.
	 * This feature is a derived union.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature Classifier</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Classifier</em>' reference.
	 * @see org.osate.aadl2.Aadl2Package#getFeature_FeatureClassifier()
	 * @model transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	FeatureClassifier getFeatureClassifier();

	/**
	 * Returns the value of the '<em><b>Classifier</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * <ul>
	 *   <li>'{@link org.osate.aadl2.Feature#getFeatureClassifier() <em>Feature Classifier</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classifier</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classifier</em>' reference.
	 * @see org.osate.aadl2.Aadl2Package#getFeature_Classifier()
	 * @model transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	ComponentClassifier getClassifier();

	/**
	 * Returns the value of the '<em><b>Refined</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * <ul>
	 *   <li>'{@link org.osate.aadl2.RefinableElement#getRefinedElement() <em>Refined Element</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Refined</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refined</em>' reference.
	 * @see #setRefined(Feature)
	 * @see org.osate.aadl2.Aadl2Package#getFeature_Refined()
	 * @model ordered="false"
	 * @generated
	 */
	Feature getRefined();

	/**
	 * Sets the value of the '{@link org.osate.aadl2.Feature#getRefined <em>Refined</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refined</em>' reference.
	 * @see #getRefined()
	 * @generated
	 */
	void setRefined(Feature value);

	/**
	 * Return the feature and any of the features it refines
	 * @return EList of feature
	 */
	// XXX: [AADL 1 -> AADL 2] Added to make instantiation and property lookup work.
	EList<Feature> getAllFeatureRefinements();

	/**
	 * Get the classifier of given feature or its refinement ancestor, if it has one, otherwise null.
	 * @return The classifier, or <code>null</code> if no classifier.
	 */
	// XXX: [AADL 1 -> AADL 2] Added to make instantiation work.
	ComponentClassifier getAllClassifier();

} // Feature
