/*
 * Copyright (c) 2015-2015 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.vladsch.idea.multimarkdown.language;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.vladsch.idea.multimarkdown.MultiMarkdownBundle;
import com.vladsch.idea.multimarkdown.parser.MultiMarkdownLexer;
import com.vladsch.idea.multimarkdown.psi.MultiMarkdownNamedElement;
import com.vladsch.idea.multimarkdown.psi.MultiMarkdownWikiLinkAnchor;
import com.vladsch.idea.multimarkdown.psi.MultiMarkdownWikiLinkRef;
import com.vladsch.idea.multimarkdown.psi.MultiMarkdownWikiLinkText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.vladsch.idea.multimarkdown.psi.MultiMarkdownTypes.*;

public class MultiMarkdownFindUsagesProvider implements FindUsagesProvider {

    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        /**
         * Creates a new instance of the words scanner.
         *
         * @param lexer              the lexer used for breaking the text into tokens.
         * @param identifierTokenSet the set of token types which represent identifiers.
         * @param commentTokenSet    the set of token types which represent comments.
         * @param literalTokenSet    the set of token types which represent literals.
         * @param skipCodeContextTokenSet the set of token types which should not be considered as code context.
         */

        DefaultWordsScanner wordsScanner = new DefaultWordsScanner(new MultiMarkdownLexer(),
                TokenSet.create(WIKI_LINK_REF, WIKI_LINK_TEXT),
                TokenSet.create(COMMENT),
                TokenSet.EMPTY,
                TokenSet.EMPTY) {
            @Override
            public int getVersion() {
                return super.getVersion() + 7;
            }
        };

        return wordsScanner;
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof MultiMarkdownNamedElement;
    }

    @Nullable
    @Override
    public String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @NotNull
    @Override
    public String getType(@NotNull PsiElement element) {
        if (element instanceof MultiMarkdownWikiLinkRef) {
            return MultiMarkdownBundle.message("findusages.wikilink.page-ref");
        } else if (element instanceof MultiMarkdownWikiLinkText) {
            return MultiMarkdownBundle.message("findusages.wikilink.page-title");
        } else if (element instanceof MultiMarkdownWikiLinkAnchor) {
            return MultiMarkdownBundle.message("findusages.wikilink.page-anchor");
        } else {
            return "";
        }
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof MultiMarkdownNamedElement) {
            return ((MultiMarkdownNamedElement) element).getDisplayName();
        } else {
            return "";
        }
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        if (element instanceof MultiMarkdownNamedElement) {
            return "";
        } else {
            return "";
        }
    }
}
