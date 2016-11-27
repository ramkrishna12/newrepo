/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Project Bibliotheca (Leo Ackerman)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package za.co.scrinium.ecommerce.web.controller;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import za.co.scrinium.ecommerce.core.service.ProductService;
import za.co.scrinium.ecommerce.events.product.RequestProductByIdEvent;
import za.co.scrinium.ecommerce.web.domain.Basket;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static za.co.scrinium.ecommerce.web.controller.fixture.WebDataFixture.singleProduct;

public class ProductIntegrationTest extends ControllerIntegrationTest {

  private static final String VIEW_NAME = "showProduct";
  private static final String FORWARDED_URL = "/WEB-INF/views/showProduct.html";

  @InjectMocks
  private ProductController controller;

  @Mock
  private Basket basket;

  @Mock
  private ProductService productService;

  public void setup() {
    setMockMvc(standaloneSetup(controller).setViewResolvers(viewResolver()).build());
  }

  @Test
  public void thatViewBasket() throws Exception {
    when(productService.requestProductById(any(RequestProductByIdEvent.class))).thenReturn(singleProduct());

    getMockMvc().perform(get("/" + VIEW_NAME + "/1")).andDo(print()).andExpect(status().isOk())
            .andExpect(model().attributeExists("product")).andExpect(view().name(is(VIEW_NAME)))
            .andExpect(forwardedUrl(FORWARDED_URL));
  }
}