/*Copyright [2018] [Kürsat Aydinli & Remo Schenker]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package ase.rsse.apirec.transactions;

public enum NodeType {
	// composite types
	DelegateDeclaration,
	EventDeclaration,
	FieldDeclaration,
	MethodDeclaration,
	PropertyDeclaration,
	VariableDeclaration,
	Assignment,
	BreakStatement,
	ContinueStatement,
	ExpressionStatement,
	GotoStatement,
	LabelledStatement,
	ReturnStatement,
	ThrowStatement,
	EventSubscriptionStatement,
	DoLoop,
	ForEachLoop,
	ForLoop,
	IfElseBlock,
	LockBlock,
	SwitchBlock,
	TryBlock,
	UncheckedBlock,
	UnsafeBlock,
	UsingBlock,
	WhileLoop,
	BinaryExpression,
	CastExpression,
	CompletionExpression,
	ComposedExpression,
	IfElseExpression,
	IndexAccessExpression,
	InvocationExpression,
	LambdaExpression,
	TypeCheckExpression,
	UnaryExpression,
	LoopHeaderBlockExpression,
	ConstantValueExpression,
	NullExpression,
	ReferenceExpression,
	EventReference,
	FieldReference,
	IndexAccessReference,
	MethodReference,
	PropertyReference,
	VariableReference,
	UnknownExpression,
	UnknownStatement,
	// DelegateDeclaration
	DelegateTypeName,
	DelegateTypeFullName,
	NamespaceName,
	// MethodDeclaration
	MethodName,
	TypeParameterName,
	ParameterName,
	TypeName,
	Statement,
	// ApiRec Node Types
	VariableDeclarationStatement,
	ParameterizedType,
	SimpleType,
	SimpleName,
	VariableDeclarationFragement,
	ClassInstanceCreation,
	MethodInvocation,		
}
