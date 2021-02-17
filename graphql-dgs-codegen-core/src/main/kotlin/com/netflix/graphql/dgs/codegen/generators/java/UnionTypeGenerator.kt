/*
 *
 *  Copyright 2020 Netflix, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.netflix.graphql.dgs.codegen.generators.java

import com.netflix.graphql.dgs.codegen.CodeGenConfig
import com.netflix.graphql.dgs.codegen.CodeGenResult
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import graphql.language.TypeName
import graphql.language.UnionTypeDefinition
import javax.lang.model.element.Modifier

class UnionTypeGenerator(private val config: CodeGenConfig) {
    fun generate(definition: UnionTypeDefinition): CodeGenResult {
        val javaType = TypeSpec.interfaceBuilder(definition.name)
                .addModifiers(Modifier.PUBLIC)

        val memberTypes = definition.memberTypes.asSequence()
                .filterIsInstance<TypeName>()
                .map { member -> ClassName.get(packageName, member.name) }
                .toList()

        if (memberTypes.isNotEmpty()) {
            javaType.addAnnotation(jsonTypeInfoAnnotation())
            javaType.addAnnotation(jsonSubTypeAnnotation(memberTypes))
        }

        val javaFile = JavaFile.builder(packageName, javaType.build()).build()
        return CodeGenResult(interfaces = listOf(javaFile))
    }

    val packageName = config.packageNameTypes
}
