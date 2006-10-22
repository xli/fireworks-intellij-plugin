/*
 *    Copyright (c) 2006 LiXiao.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.thoughtworks.fireworks.adapters.astnode;

import com.intellij.lang.Language;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public class IgnoredTokenSet {

    private TokenSet defaultSet;

    public IgnoredTokenSet() {
        defaultSet = TokenSet.orSet(new TokenSet[]{whitespaceAndJavaDiv(), JavaDocTokenType.ALL_JAVADOC_TOKENS});
    }

    private TokenSet whitespaceAndJavaDiv() {
        return TokenSet.create(new IElementType[]{TokenType.WHITE_SPACE});
    }

    public TokenSet getByLanguage(Language language) {
        if (language.getParserDefinition() == null) {
            return defaultSet;
        }
        TokenSet languageCommontTokens = language.getParserDefinition().getCommentTokens();
        TokenSet languageWhitespaceTokens = language.getParserDefinition().getWhitespaceTokens();
        return TokenSet.orSet(new TokenSet[]{defaultSet, languageCommontTokens, languageWhitespaceTokens});
    }
}
