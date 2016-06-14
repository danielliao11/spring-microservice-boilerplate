/**
 * Package of RESTFul parameters.
 * <p>
 *   Every param bean must extends
 *   {@link com.saintdan.framework.param.BaseParam}
 *
 *   Now you can use
 *   `isSignValid(String publicKey)` function to validate the signature is correct or not.
 *
 *   Use `sign(String privateKey)` function to sign.
 *
 *   Use `getIncorrectParams()` to get null fields.
 * </p>
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 9/22/15
 * @since JDK1.8
 */
package com.saintdan.framework.param;