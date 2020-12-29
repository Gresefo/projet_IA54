/**
 * $Id$
 * 
 * SARL is an general-purpose agent programming language.
 * More details on http://www.sarl.io
 * 
 * Copyright (C) 2014-2020 the original authors or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sarl.demos.ants;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;

/**
 * General configuration of the boids simulation.
 * 
 * @author Nicolas Gaud
 */
@SarlSpecification("0.10")
@SarlElementType(11)
@SuppressWarnings("all")
public interface Settings {
  /**
   * Environmental grid default width
   */
  public static final int EnvtWidth = 1000;
  
  /**
   * Environmental grid default height
   */
  public static final int EnvtHeight = 1000;
  
  /**
   * Boolean specifying whether message logs are activated or not
   */
  public static final boolean isLogActivated = true;
  
  /**
   * Specify a pause delay before each boids sends his influence to the environment, and respectively before the environment sends perceptions to boids
   */
  public static final int pause = 0;
  
  public static final int iteration = 5000;
  
  public static final int alpha = 1;
  
  public static final int beta = 2;
  
  public static final double rho = 0.5;
}
