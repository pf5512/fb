package com.footballer.util;

import static java.util.Locale.ENGLISH;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.footballer.rest.api.v1.exception.CopyException;

/**
* This is an uitility class to copy values for two instances if they have same
* attributes.
*/

public final class CopyUtil {

   private CopyUtil() {
   }

   /**
    * Copy the property values of the given source bean into the given target
    * bean.
    * <p/>
    * Note: The source and target classes do not have to match or even be derived
    * from each other, as long as the properties match. Any bean properties that
    * the source bean exposes but the target bean does not will silently be
    * ignored.
    *
    * @param source           the source bean
    * @param target           the target bean
    * @param editable         the class (or interface) to restrict property setting to
    * @param ignoreProperties array of property names to ignore
    * @param copyIfNull       flag of if the value is null, need to copy it or not.
    */
   private static void copy(Object target, Object source, Class<?> editable,
                            String[] ignoreProperties, Boolean copyIfNull, Boolean force) throws Exception {
       Assert.notNull(source, "Source must not be null");
       Assert.notNull(target, "Target must not be null");

       Class<?> actualEditable = target.getClass();
       if (editable != null) {
           if (!editable.isInstance(target)) {
               throw new IllegalArgumentException("Target class ["
                       + target.getClass().getName()
                       + "] not assignable to Editable class [" + editable.getName() + "]");
           }
           actualEditable = editable;
       }
       PropertyDescriptor[] targetPds = BeanUtils
               .getPropertyDescriptors(actualEditable);
       List<?> ignoreList = (ignoreProperties != null) ? Arrays
               .asList(ignoreProperties) : null;

       for (int i = 0; i < targetPds.length; i++) {
           PropertyDescriptor targetPd = targetPds[i];
           if (targetPd.getWriteMethod() != null
                   && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
               PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source
                       .getClass(), targetPd.getName());
               if (sourcePd != null) {
                   // deal with java.lang.Boolean type.
                   if (Boolean.class.equals(sourcePd.getPropertyType())
                           || boolean.class.equals(sourcePd.getPropertyType())) {
                       String name = sourcePd.getName();
                       if (name != null) {
                           // if type of attribute is boolean, according to JavaBean
                           // specification,
                           // setter and getter should be like : setXXX(boolean parameter)
                           // and isXXX()
                           name = "is" + name.substring(0, 1).toUpperCase(ENGLISH)
                                   + name.substring(1);
                           Class<? extends Object> sourceClass = source.getClass();

                           Method method = null;

                           method = findMethod(sourceClass, name);

                           // if can't find ,that means, the type of attribute is
                           // java.lang.Boolean.
                           // then setter and getter should be like: setXXX(Boolean
                           // parameter) and getXXX()
                           if (method == null) {
                               name = "get" + name.substring(2, name.length());
                               method = findMethod(sourceClass, name);
                           }
                           // write value if get the value by getter method.Condister both
                           // isXXX and getXXX.
                           if (method != null) {
                               if (!Modifier.isPublic(method.getDeclaringClass()
                                       .getModifiers())) {
                                   method.setAccessible(true);
                               }
                               Object value = method.invoke(source, new Object[0]);
                               writeValue(target, copyIfNull, targetPd, value);
                           }
                       }
                   } else {
                       if (sourcePd.getReadMethod() != null) {
                           try {
                               Method readMethod = sourcePd.getReadMethod();
                               if (!Modifier.isPublic(readMethod.getDeclaringClass()
                                       .getModifiers())) {
                                   readMethod.setAccessible(true);
                               }
                               Object value = readMethod.invoke(source, new Object[0]);
                               writeValue(target, copyIfNull, targetPd, value);
                           } catch (java.lang.IllegalArgumentException ex) {
                               if (force) {
                                   continue;
                               } else {
                                   throw new RuntimeException(
                                           "Could not copy properties from source to target", ex);
                               }
                           } catch (Throwable t) {
                               throw new RuntimeException(
                                       "Could not copy properties from source to target", t);
                           }
                       }
                   }
               }
           }
       }
   }

   /**
    * Find method by given name
    *
    * @param clazz
    * @param name
    * @return
    */
   private static Method findMethod(Class<?> clazz, String name) {
       Method target = null;
       for (Method m : clazz.getMethods()) {
           if (m.getName().equals(name)) {
               target = m;
               break;
           }
       }
       return target;
   }

   /**
    * @param target
    * @param copyIfNull
    * @param targetPd
    * @param value
    * @throws IllegalAccessException
    * @throws InvocationTargetException
    */
   private static void writeValue(Object target, Boolean copyIfNull,
                                  PropertyDescriptor targetPd, Object value) throws IllegalAccessException,
           InvocationTargetException {
       Method writeMethod = targetPd.getWriteMethod();
       if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
           writeMethod.setAccessible(true);
       }
       // if copyIfNull is true, then need to copy whatever the value is .
       if (copyIfNull) {
           writeMethod.invoke(target, new Object[]{value});
       } else {
           // if copyIfNull is not true, don't need to copy if the value is null.
           if (value != null) {
               writeMethod.invoke(target, new Object[]{value});
           }
       }
   }

   /**
    * CopyOfNull is a default value: true,that means, copy everything even the
    * value is null.
    */
   public static void copy(Object dest, Object orig) throws CopyException {
       if (dest != null && orig != null) {
           try {
               copy(dest, orig, null, null, Boolean.TRUE, Boolean.FALSE);
           } catch (Exception e) {
               String message = "CopyUtil.copy(Object dest, Object orig) error---> "
                       + e.getMessage();
               throw new CopyException(message);
           }
       }
   }

   /**
    * Copy the java beans forcedly even there is any run time exception happens
    */
   public static void copyForcedly(Object dest, Object orig) {
       if (dest != null && orig != null) {
           try {
               copy(dest, orig, null, null, Boolean.FALSE, Boolean.TRUE);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }

   /**
    * Copy the java beans forcedly even there is any run time exception happens
    */
   public static void copyForcedly(Object dest, Object orig, Boolean copyIfNull) {
       if (dest != null && orig != null) {
           try {
               copy(dest, orig, null, null, copyIfNull, Boolean.TRUE);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
   }

   /**
    *
    */
   public static void copy(Object dest, Object orig, Boolean copyIfNull) throws CopyException {
       if (dest != null && orig != null) {
           try {
               copy(dest, orig, null, null, copyIfNull, Boolean.FALSE);
           } catch (Exception e) {
               String message = "CopyUtil.copy(Object dest, Object orig) error---> "
                       + e.getMessage();
               throw new CopyException(message);
           }
       }
   }
   
   // List  深拷贝  会创建新的内存空间 使用 执行序列化和反序列化  进行深度拷贝，
   public static <T> List<T> listDeepCopy(List<T> src) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		@SuppressWarnings("unchecked")
		List<T> dest = (List<T>) in.readObject();
		return dest;
	}
}


