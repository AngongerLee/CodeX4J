package com.croot.gencode.common;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * 对象属性值对拷
 * <p>
 * 两个bean中，具备以下条件的POJO之间可以进行属性对拷
 * <li>field名称一样,且必须有get/set方法
 * <li>如果field是简单类型，则field之间的类型必须一致
 * <li>如果field是复合类型，则复合类型中的成员变量需要遵循上边的两个条件
 * </p>
 *
 */


public class BeanUtil {
    /**
     * 复制对象属性
     *
     * @param from
     * @param to
     */
    public static void copyProperties(Object from, Object to) {
        // 如果对拷对象是简单类型，则直接忽略
        if (isSimpleObject(from) || isSimpleObject(to)) {
            return;
        } else {
            Field[] fields = getAllField(from.getClass());
            for (Field tempField : fields) {
                // 不关心final类型的字段
                if (!Modifier.isFinal(tempField.getModifiers())) {
                    PropertyDescriptor fromPd = null;
                    try {
                        fromPd = new PropertyDescriptor(tempField.getName(), from.getClass());
                        Method getMethod = fromPd.getReadMethod();
                        Object value = getMethod.invoke(from, null);
                        if (null != value) {
                            if (isSimpleObject(value)) {// 简单对象处理
                                PropertyDescriptor toPd = new PropertyDescriptor(tempField.getName(), to.getClass());
                                Method setMethod = toPd.getWriteMethod();
                                setMethod.invoke(to, value);
                            } else if (value.getClass().isArray()) {// 数组类型处理
                                arrayCopy(tempField, value, to);

                            } else if (value instanceof java.util.List) {
                                listCopy(tempField, value, to);

                            } else {// 复杂类型则需要继续递归
                                complexCopy(tempField, value, to);
                            }
                        }
                    } catch (IntrospectionException e) {
                        continue;
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (IllegalArgumentException e1) {
                        e1.printStackTrace();
                    } catch (InvocationTargetException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }

    /**
     * 判断是否简单对象
     *
     * @param o
     * @return
     */
    private static boolean isSimpleObject(Object o) {
        Class<?> type = o.getClass();
        if (type.isPrimitive()) { // 基本类型
            return true;
        }


        // 不可更改的变量类型 如 String，Long
        if (type.equals(String.class))
            return true;
        if (type.equals(Long.class))
            return true;
        if (type.equals(Boolean.class))
            return true;
        if (type.equals(Short.class))
            return true;
        if (type.equals(Integer.class))
            return true;
        if (type.equals(Character.class))
            return true;
        if (type.equals(Float.class))
            return true;
        if (type.equals(Double.class))
            return true;
        if (type.equals(Byte.class))
            return true;

        return false;
    }

    /**
     * 数组对象拷贝
     *
     * @param tempField
     * @param from
     * @param to
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private static void arrayCopy(Field tempField, Object from, Object to)
            throws NoSuchFieldException, SecurityException, InstantiationException,
            IllegalAccessException, IntrospectionException,
            IllegalArgumentException, InvocationTargetException {
        int len = Array.getLength(from);
        // 创建目标对象的成员变量
        Field toField = to.getClass().getDeclaredField(tempField.getName());
        Object array = Array.newInstance(toField.getType().getComponentType(), len);
        for (int i = 0; i < len; i++) {
            Object toItemObj = toField.getType().getComponentType().newInstance();
            Object fromItemObj = Array.get(from, i);
            if (fromItemObj != null) {
                if (isSimpleObject(fromItemObj)) {
                    toItemObj = fromItemObj;
                } else {
                    copyProperties(fromItemObj, toItemObj);

                }
                Array.set(array, i, toItemObj);
            }
        }
        PropertyDescriptor toPd = new PropertyDescriptor(tempField.getName(), to.getClass());
        Method setMethod = toPd.getWriteMethod();
        setMethod.invoke(to, array);
    }

    /**
     * 完成列表成员变量的拷贝
     *
     * @param tempField
     * @param from
     * @param to
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private static void listCopy(Field tempField, Object from, Object to)
            throws NoSuchFieldException, SecurityException, InstantiationException,
            IllegalAccessException, IntrospectionException,
            IllegalArgumentException, InvocationTargetException {
        // 创建目标对象的成员变量
        Field toField = to.getClass().getDeclaredField(tempField.getName());
        List toList = null;
        // 根据具体的列表类型来判断
        if (from instanceof ArrayList) {
            toList = new ArrayList();
        } else if (from instanceof LinkedList) {
            toList = new LinkedList();
        }

        List<?> fromList = (List) from;
        for (Object tempItem : fromList) {
            ParameterizedType pt = (ParameterizedType) toField.getGenericType();
            Class<?> clz = (Class) pt.getActualTypeArguments()[0];
            Object toItemObj = clz.newInstance();
            if (tempItem != null) {
                if (isSimpleObject(tempItem)) {
                    toItemObj = tempItem;
                } else {
                    copyProperties(tempItem, toItemObj);

                }
                toList.add(toItemObj);
            }
        }

        PropertyDescriptor toPd = new PropertyDescriptor(tempField.getName(), to.getClass());
        Method setMethod = toPd.getWriteMethod();
        setMethod.invoke(to, toList);
    }

    /**
     * 复杂对象拷贝
     *
     * @param tempField
     * @param from
     * @param to
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private static void complexCopy(Field tempField, Object from, Object to)
            throws NoSuchFieldException, SecurityException, InstantiationException,
            IllegalAccessException, IntrospectionException,
            IllegalArgumentException, InvocationTargetException {
        Field toField = getDeclaredField(to.getClass(), tempField.getName());
        Object toFieldObj;
        try {
            toFieldObj = toField.getType().newInstance();
            PropertyDescriptor toPd = new PropertyDescriptor(tempField.getName(), to.getClass());
            Method setMethod = toPd.getWriteMethod();
            setMethod.invoke(to, toFieldObj);
            copyProperties(from, toFieldObj);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取类clazz的所有Field，包括其父类的Field，如果重名，以子类Field为准。
     *
     * @param clazz
     * @return Field数组
     */
    private static Field[] getAllField(Class<?> clazz) {
        ArrayList<Field> fieldList = new ArrayList<Field>();
        Field[] dFields = clazz.getDeclaredFields();
        if (null != dFields && dFields.length > 0) {
            fieldList.addAll(Arrays.asList(dFields));
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != Object.class) {
            Field[] superFields = getAllField(superClass);
            if (null != superFields && superFields.length > 0) {
                for (Field field : superFields) {
                    if (!isContain(fieldList, field)) {
                        fieldList.add(field);
                    }
                }
            }
        }
        Field[] result = new Field[fieldList.size()];
        fieldList.toArray(result);
        return result;
    }

    /**
     * 检测Field List中是否已经包含了目标field
     *
     * @param fieldList
     * @param field     带检测field
     * @return
     */
    private static boolean isContain(ArrayList<Field> fieldList, Field field) {
        for (Field temp : fieldList) {
            if (temp.getName().equals(field.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取field信息，如果子类中没有，则从其父类中获取
     *
     * @param clazz    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    private static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Field field = null;


        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                // 如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

            }
        }

        return null;
    }
}
