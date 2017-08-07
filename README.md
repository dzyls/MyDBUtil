# MyDBUtil
MyDBUtils
一个简单的工具类，
其中使用到了Jdbc,泛型,反射以及注解，
使用此工具类需要指定db.properties的目录（不指定默认在src/prop/db.properties）
此工具类要求表和Bean的名字、结构相同，Bean的主键需要设置注解（标明主键）
对数据库增改只需传入一个Bean实例，查删传入class和主键即可
