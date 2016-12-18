package application;

/*
 * Copyright (c) 2015 VA programming
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class MaskField extends TextField {


    /**
     * Ð¿Ð¾Ð·Ð¸Ñ†Ð¸Ñ� Ð² Ð¼Ð°Ñ�ÐºÐµ Ð¿Ð¾Ð·Ð²Ð¾Ð»Ð¸Ñ‚ Ð²Ð²ÐµÑ�Ñ‚Ð¸ Ñ‚Ð¾Ð»ÑŒÐºÐ¾ Ñ†Ð¸Ñ„Ñ€Ñ‹
     */
    public static final char MASK_DIGIT = 'D';

    /**
     * Ð¿Ð¾Ð·Ð¸Ñ†Ð¸Ñ� Ð² Ð¼Ð°Ñ�ÐºÐµ Ð¿Ð¾Ð·Ð²Ð¾Ð»Ð¸Ñ‚ Ð²Ð²ÐµÑ�Ñ‚Ð¸ Ð±ÑƒÐºÐ²Ñ‹ Ð¸ Ñ†Ð¸Ñ„Ñ€Ñ‹
     */
    public static final char MASK_DIG_OR_CHAR = 'W';

    /**
     * Ð¿Ð¾Ð·Ð¸Ñ†Ð¸Ñ� Ð² Ð¼Ð°Ñ�ÐºÐµ Ð¿Ð¾Ð·Ð²Ð¾Ð»Ð¸Ñ‚ Ð²Ð²ÐµÑ�Ñ‚Ð¸ Ñ‚Ð¾Ð»ÑŒÐºÐ¾ Ð±ÑƒÐºÐ²Ñ‹
     */
    public static final char MASK_CHARACTER = 'A';


    public static final char WHAT_MASK_CHAR = '#';
    public static final char WHAT_MASK_NO_CHAR = '-';


    public static final char PLACEHOLDER_CHAR_DEFAULT = '_';


    private List<Position> objectMask = new ArrayList<>();

    /**
     * Ð¿Ñ€Ð¾Ñ�Ñ‚Ð¾Ð¹ Ñ‚ÐµÐºÑ�Ñ‚ Ð±ÐµÐ· Ð¿Ñ€Ð¸Ð¼ÐµÐ½ÐµÐ½Ð¸Ñ� Ð¼Ð°Ñ�ÐºÐ¸
     */
    private StringProperty plainText;

    public final String getPlainText() {
        return plainTextProperty().get();
    }

    public final void setPlainText(String value) {
        plainTextProperty().set(value);
        updateShowingField();
    }

    public final StringProperty plainTextProperty() {
        if (plainText == null)
            plainText = new SimpleStringProperty(this, "plainText", "");
        return plainText;
    }


    /**
     * Ñ�Ñ‚Ð¾ Ñ�Ð°Ð¼Ð° Ð¼Ð°Ñ�ÐºÐ° Ð²Ð¸Ð´Ð¸Ð¼Ð°Ñ� Ð² Ð¿Ð¾Ð»Ðµ Ð²Ð²Ð¾Ð´Ð°
     */
    private StringProperty mask;

    public final String getMask() {
        return maskProperty().get();
    }

    public final void setMask(String value) {
        maskProperty().set(value);
        rebuildObjectMask();
        updateShowingField();
    }

    public final StringProperty maskProperty() {
        if (mask == null)
            mask = new SimpleStringProperty(this, "mask");

        return mask;
    }


    /**
     * ÐµÑ�Ð»Ð¸ Ð¼Ð°Ñ�ÐºÐ° Ð´Ð¾Ð»Ð¶Ð½Ð° Ð¾Ñ‚Ð¾Ð±Ñ€Ð°Ð¶Ð°Ñ‚ÑŒ Ñ�Ð¸Ð¼Ð²Ð¾Ð»Ñ‹ ÐºÐ¾Ñ‚Ð¾Ñ€Ñ‹Ðµ Ð·Ð°Ñ€ÐµÐ·ÐµÑ€Ð²Ð¸Ñ€Ð¾Ð²Ð°Ð½Ñ‹ Ð´Ð»Ñ� Ð¼Ð°Ñ�ÐºÐ¸, Ñ‚Ð¾ Ð·Ð°Ð´Ð°ÐµÑ‚Ñ�Ñ� Ð´Ð¾Ð¿Ð¾Ð»Ð½Ð¸Ñ‚ÐµÐ»ÑŒÐ½Ð°Ñ� Ð¿Ð¾Ð´Ñ�ÐºÐ°Ð·ÐºÐ° Ð³Ð´Ðµ Ñ�Ð¸Ð¼Ð²Ð¾Ð» Ð¼Ð°Ñ�ÐºÐ¸, Ð° Ð³Ð´Ðµ Ð¿Ñ€Ð¾Ñ�Ñ‚Ð¾ Ñ�Ð¸Ð¼Ð²Ð¾Ð»
     */
    private StringProperty whatMask;

    public final String getWhatMask() {
        return whatMaskProperty().get();
    }

    public final void setWhatMask(String value) {
        whatMaskProperty().set(value);
        rebuildObjectMask();
        updateShowingField();
    }

    public final StringProperty whatMaskProperty() {
        if (whatMask == null) {
            whatMask = new SimpleStringProperty(this, "whatMask");
        }
        return whatMask;
    }


    /**
     * Ñ�Ñ‚Ð¾ Ñ�Ð¸Ð¼Ð²Ð¾Ð»Ñ‹ Ð·Ð°Ð¼ÐµÑ‰ÐµÐ½Ð¸Ñ�
     */
    private StringProperty placeholder;

    public final String getPlaceholder() {
        return placeholderProperty().get();
    }

    public final void setPlaceholder(String value) {
        placeholderProperty().set(value);
        rebuildObjectMask();
        updateShowingField();
    }

    public final StringProperty placeholderProperty() {
        if (placeholder == null)
            placeholder = new SimpleStringProperty(this, "placeholder");
        return placeholder;
    }


    private class Position {
        public char mask;
        public char whatMask;
        public char placeholder;

        public Position(char mask, char whatMask, char placeholder) {
            this.mask = mask;
            this.placeholder = placeholder;
            this.whatMask = whatMask;
        }

        public boolean isPlainCharacter()
        {
            return whatMask == WHAT_MASK_CHAR;
        }

        public boolean isCorrect(char c)
        {
            switch (mask)
            {
                case MASK_DIGIT:
                    return Character.isDigit(c);
                case MASK_CHARACTER:
                    return Character.isLetter(c);
                case MASK_DIG_OR_CHAR:
                    return Character.isLetter(c) || Character.isDigit(c);
            }
            return false;
        }
    }


    /**
     * Ñ„Ð¾Ñ€Ð¼Ð¸Ñ€ÑƒÐµÑ‚ Ñ�Ð¿Ð¸Ñ�Ð¾Ðº Ð¾Ð±ÑŠÐµÐºÑ‚Ð¾Ð² Position Ð¿Ð¾ ÐºÐ°Ð¶Ð´Ð¾Ð¼Ñƒ Ñ�Ð¸Ð¼Ð²Ð¾Ð»Ñƒ Ð¼Ð°Ñ�ÐºÐ¸
     */
    private void rebuildObjectMask() {
        objectMask = new ArrayList<>();

        for (int i = 0; i < getMask().length(); i++) {
            char m = getMask().charAt(i);
            char w = WHAT_MASK_CHAR;
            char p = PLACEHOLDER_CHAR_DEFAULT;

            if (getWhatMask() != null && i < getWhatMask().length()) {
                //ÐºÐ¾Ð½ÐºÑ€ÐµÑ‚Ð½Ð¾ ÑƒÐºÐ°Ð·Ð°Ð½Ð¾ Ñ�Ð¸Ð¼Ð²Ð¾Ð» Ð¼Ð°Ñ�ÐºÐ¸ Ñ�Ñ‚Ð¾ Ð¸Ð»Ð¸ Ð½ÐµÑ‚
                if (getWhatMask().charAt(i) != WHAT_MASK_CHAR) {
                    w = WHAT_MASK_NO_CHAR;
                }
            }
            else
            {
                //Ñ‚Ð°Ðº ÐºÐ°Ðº Ð½Ðµ ÑƒÐºÐ°Ð·Ð°Ð½Ð¾ Ñ‡Ñ‚Ð¾ Ð·Ð° Ñ�Ð¸Ð¼Ð²Ð¾Ð» - Ð¿Ð¾Ð½Ð¸Ð¼Ð°ÐµÐ¼ Ñ�Ð°Ð¼Ð¾Ñ�Ñ‚Ð¾Ñ�Ñ‚ÐµÐ»ÑŒÐ½Ð¾
                //Ð¸ ÐµÑ�Ð»Ð¸ Ñ�Ð¸Ð¼Ð²Ð¾Ð» Ð½Ðµ Ð½Ð°Ñ…Ð¾Ð´Ð¸Ñ‚Ñ�Ñ� Ñ�Ñ€ÐµÐ´Ð¸ Ñ�Ð¸Ð¼Ð²Ð¾Ð»Ð¾Ð² Ð¼Ð°Ñ�ÐºÐ¸ - Ñ‚Ð¾ Ñ�Ñ‚Ð¾ Ñ�Ñ‡Ð¸Ñ‚Ð°ÐµÑ‚Ñ�Ñ� Ð¿Ñ€Ð¾Ñ�Ñ‚Ñ‹Ð¼ Ð»Ð¸Ñ‚ÐµÑ€Ð°Ð»Ð¾Ð¼
                if (m != MASK_CHARACTER && m != MASK_DIG_OR_CHAR && m != MASK_DIGIT)
                    w = WHAT_MASK_NO_CHAR;

            }

            if (getPlaceholder() != null && i < getPlaceholder().length())
                p = getPlaceholder().charAt(i);

            objectMask.add(new Position(m, w, p));
        }
    }


    /**
     * Ñ„ÑƒÐ½ÐºÑ†Ð¸Ñ� ÐºÐ°Ðº Ð±Ñ‹ Ð½Ð°ÐºÐ»Ð°Ð´Ñ‹Ð²Ð°ÐµÑ‚ Ð¿Ñ€Ð¾Ñ�Ñ‚Ð¾ Ñ‚ÐµÐºÑ�Ñ‚ plainText Ð½Ð° Ð·Ð°Ð´Ð°Ð½Ð½ÑƒÑŽ Ð¼Ð°Ñ�ÐºÑƒ,
     * ÐºÐ¾Ñ€Ñ€ÐµÐºÑ‚Ð¸Ñ€ÑƒÐµÑ‚ Ð¿Ð¾Ð·Ð¸Ñ†Ð¸ÑŽ ÐºÐ°Ñ€ÐµÑ‚ÐºÐ¸
     */
    private void updateShowingField()
    {
        int counterPlainCharInMask = 0;
        int lastPositionPlainCharInMask = 0;
        int firstPlaceholderInMask = -1;
        String textMask = "";
        String textPlain = getPlainText();
        for (int i = 0; i < objectMask.size(); i++) {
            Position p = objectMask.get(i);
            if (p.isPlainCharacter()) {
                if (textPlain.length() > counterPlainCharInMask) {

                    char c = textPlain.charAt(counterPlainCharInMask);
                    while (!p.isCorrect(c))
                    {
                        //Ð²Ñ‹Ñ€ÐµÐ·Ð°ÐµÐ¼ Ñ‚Ð¾ Ñ‡Ñ‚Ð¾ Ð½Ðµ Ð¿Ð¾Ð´Ð¾ÑˆÐ»Ð¾
                        textPlain = textPlain.substring(0, counterPlainCharInMask) + textPlain.substring(counterPlainCharInMask + 1);

                        if (textPlain.length() > counterPlainCharInMask)
                            c = textPlain.charAt(counterPlainCharInMask);
                        else
                            break;
                    }

                    textMask += c;
                    lastPositionPlainCharInMask = i;
                }
                else {
                    textMask += p.placeholder;
                    if (firstPlaceholderInMask == -1)
                        firstPlaceholderInMask = i;
                }

                counterPlainCharInMask++;

            } else {
                textMask += p.mask;
            }
        }

        setText(textMask);

        if (firstPlaceholderInMask == -1)
            firstPlaceholderInMask = 0;

        int caretPosition = (textPlain.length() > 0 ? lastPositionPlainCharInMask + 1 : firstPlaceholderInMask);
        selectRange(caretPosition, caretPosition);

        if (textPlain.length() > counterPlainCharInMask)
            textPlain = textPlain.substring(0, counterPlainCharInMask);

        if (!textPlain.equals(getPlainText()))
            setPlainText(textPlain);

    }



    private int interpretMaskPositionInPlainPosition(int posMask)
    {
        int posPlain = 0;

        for (int i = 0; i < objectMask.size() && i < posMask; i++) {
            Position p = objectMask.get(i);
            if (p.isPlainCharacter())
                posPlain++;
        }

        return posPlain;
    }


    @Override
    public void replaceText(int start, int end, String text) {


        int plainStart = interpretMaskPositionInPlainPosition(start);
        int plainEnd = interpretMaskPositionInPlainPosition(end);

        String plainText1 = "";
        if (getPlainText().length() > plainStart)
            plainText1 = getPlainText().substring(0, plainStart);
        else
            plainText1 = getPlainText();


        String plainText2 = "";
        if (getPlainText().length() > plainEnd)
            plainText2 = getPlainText().substring(plainEnd);
        else
            plainText2 = "";


        setPlainText(plainText1 + text + plainText2);

    }


}
