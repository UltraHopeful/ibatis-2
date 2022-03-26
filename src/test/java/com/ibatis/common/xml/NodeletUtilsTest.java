/*
 * Copyright 2004-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibatis.common.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Properties;

import org.junit.jupiter.api.Test;

class NodeletUtilsTest {

  /**
   * Test for getting of a boolean argument.
   */
  @Test
  void testGetBooleanAttribute() {

    Properties props = new Properties();
    props.setProperty("boolean1", "true");
    props.setProperty("boolean2", "false");
    props.setProperty("boolean3", "TRUE");
    props.setProperty("boolean4", "def");
    props.setProperty("boolean5", "");

    assertEquals(true, GetXmlAttribute.getBooleanAttribute(props, "boolean1", false));
    assertEquals(false, GetXmlAttribute.getBooleanAttribute(props, "boolean2", true));
    assertEquals(false, GetXmlAttribute.getBooleanAttribute(props, "boolean3", false));
    assertEquals(false, GetXmlAttribute.getBooleanAttribute(props, "boolean4", true));
    assertEquals(false, GetXmlAttribute.getBooleanAttribute(props, "boolean5", true));
    assertEquals(true, GetXmlAttribute.getBooleanAttribute(props, "undef", true));
  }

  /**
   * Test for getting of a integer argument.
   */
  @Test
  void testGetIntAttribute() {

    Properties props = new Properties();
    props.setProperty("int1", "0");
    props.setProperty("int2", "1000");
    props.setProperty("int3", "-200");
    props.setProperty("int4", "undef");
    props.setProperty("int5", "");

    assertEquals(0, GetXmlAttribute.getIntAttribute(props, "int1", -10000));
    assertEquals(1000, GetXmlAttribute.getIntAttribute(props, "int2", -10000));
    assertEquals(-200, GetXmlAttribute.getIntAttribute(props, "int3", -10000));

    try {
      assertEquals(-10000, GetXmlAttribute.getIntAttribute(props, "int4", -10000));
      fail("testGetIntAttribute() should have thrown an exception");
    } catch (NumberFormatException ex) {
    }

    try {
      GetXmlAttribute.getIntAttribute(props, "int5", -10000);
      fail("testGetIntAttribute() should have thrown an exception");
    } catch (NumberFormatException ex) {
    }
  }

  /**
   * Test for getting of a boolean argument.
   */
  @Test
  void testParsePropertyTokens() {

    Properties props = new Properties();
    props.setProperty("key1", "0");
    props.setProperty("key2", "1000");
    props.setProperty("key3", "abcd");
    props.setProperty("key4", "");

    assertEquals(":1000:", NodeletUtils.parsePropertyTokens(":${key2}:", props));
    assertEquals(":1000abcd:", NodeletUtils.parsePropertyTokens(":${key2}${key3}:", props));
    assertEquals("::abcd::", NodeletUtils.parsePropertyTokens("::${key4}${key3}::", props));
    assertEquals("::undef::", NodeletUtils.parsePropertyTokens("::${undef}::", props));
  }
}
