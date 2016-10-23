/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.rebind.type;

import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.squareup.javapoet.CodeBlock;

/**
 * Contains informations about serializer or deserializer like its type or the code to instantiate it.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class JMapperType {

    protected static abstract class Builder<B extends Builder, M extends JMapperType> {

        protected boolean beanMapper;

        protected JType type;

        protected CodeBlock instance;

        protected ImmutableList<M> parameters;

        public B beanMapper( boolean beanMapper ) {
            this.beanMapper = beanMapper;
            return (B) this;
        }

        public B type( JType type ) {
            this.type = type;
            return (B) this;
        }

        public B instance( CodeBlock instance ) {
            this.instance = instance;
            return (B) this;
        }

        public B parameters( ImmutableList<M> parameters ) {
            this.parameters = parameters;
            return (B) this;
        }
    }

    protected final boolean beanMapper;

    protected final JType type;

    protected final CodeBlock instance;

    protected final ImmutableList<? extends JMapperType> parameters;

    /**
     * <p>Constructor for JMapperType.</p>
     *
     * @param beanMapper a boolean.
     * @param type a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     * @param instance a {@link com.squareup.javapoet.CodeBlock} object.
     * @param parameters a {@link com.google.gwt.thirdparty.guava.common.collect.ImmutableList} object.
     */
    protected JMapperType( boolean beanMapper, JType type, CodeBlock instance, ImmutableList<? extends JMapperType> parameters ) {
        this.beanMapper = beanMapper;
        this.type = type;
        this.instance = instance;
        this.parameters = parameters;
    }

    /**
     * <p>isBeanMapper</p>
     *
     * @return a boolean.
     */
    public final boolean isBeanMapper() {
        return beanMapper;
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return a {@link com.google.gwt.core.ext.typeinfo.JType} object.
     */
    public final JType getType() {
        return type;
    }

    /**
     * <p>Getter for the field <code>instance</code>.</p>
     *
     * @return a {@link com.squareup.javapoet.CodeBlock} object.
     */
    public final CodeBlock getInstance() {
        return instance;
    }

    /**
     * <p>Getter for the field <code>parameters</code>.</p>
     *
     * @return a {@link com.google.gwt.thirdparty.guava.common.collect.ImmutableList} object.
     */
    public final ImmutableList<? extends JMapperType> getParameters() {
        return parameters;
    }
}
