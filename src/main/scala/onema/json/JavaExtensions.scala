/**
  * This file is part of the ONEMA onema Package.
  * For the full copyright and license information,
  * please view the LICENSE file that was distributed
  * with this source code.
  *
  * copyright (c) 2017, Juan Manuel Torres (http://onema.io)
  *
  * @author Juan Manuel Torres <kinojman@gmail.com>
  */

package onema.json

import com.fasterxml.jackson.databind.ObjectMapper

import scala.reflect._
import scala.util.{Failure, Success, Try}


/**
  * Implicit class that provides extension methods to simplify serializing and de-serializing json
  */
object JavaExtensions {
  implicit class JavaPojoToJson(objectClass: Any) {
    /**
      * Some java types are not properly serialized by json4s
      *
      * @return
      */
    def asJson: String = {
      val mapper = new ObjectMapper
      mapper.writeValueAsString(objectClass)
    }
  }

  implicit class JsonStringToJavaPojo(json: String) {
    /**
      * Parses a json string to a java class of the given type parameter.
      * @tparam T type to serialize json into
      * @return object of type T
      */
    def jsonDecode[T: ClassTag]: T = {
      val classType: Class[_] = implicitly[ClassTag[T]].runtimeClass
      val mapper = new ObjectMapper()
      Try(mapper.readValue(json, classType)) match {
        case Success(result) =>
          result.asInstanceOf[T]
        case Failure(e) =>
          throw e
      }
    }
  }
}
