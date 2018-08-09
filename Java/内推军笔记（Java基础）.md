# Java 中的集合类

### ArrayList、LinkedList、Vector

- 线程安全性
- 扩容机制
- 增删改查效率

### HashMap & HashTable

#### HashMap

**HashMap 扩容机制：**

当HashMap 中的结点个数超过数组大小$* loadFactor$（加载因子）时，就会进行数组扩容，loadFactor 的默认值为 0.75。也就是说，默认情况下，数组大小为 16，那么当HashMap、中结点个数超过 $16*0.75=12$ 的时候，就把数组的大小扩展为$2*16=32$，即扩大一倍，然后重新计算每个元素在数组中的位置，并放进去，而这是一个非常消耗性能的操作。

**HashMap 多线程出现的问题：**

1. 多线程导致循环引用，get操作死循环。
2. 多线程导致元素丢失。

#### HashTable 和 HashMap 的区别

1. HashTable 是线程安全的，HashMap 不是线程安全的。
2. HashMap 的key 和value 都可以为null 值，HashTable 的key 和value 都不允许有Null 值。
3. HashMap 中数组的默认大小是16，而且一定是2 的倍数，扩容后的数组长度是之前数组长度的2 倍。HashTable 中数组默认大小是11，扩容后的数组长度是之前数组长度的2 倍+1。
4. 判断是否含有某个键：在HashMap 中，null 可以作为键，这样的键只有一个；可以有一个或多个键所对
   应的值为null。当get()方法返回null 值时，既可以表示HashMap 中没有该键，也可以表示该键所对应的值为null。因此，在HashMap 中不能用get()方法来判断HashMap 中是否存在某个键，而应该用containsKey()方法来判断。Hashtable 的键值都不能为null，所以可以用get()方法来判断是否含有某个键。

### ConcurrentHashMap

- 在ConcurrentHashMap 中，不允许用null 作为键和值。
- ConcurrentHashMap 使用分段锁技术，将数据分成一段一段的存储，然后给每一段数据配一把锁，两段数据可以同时写。读操作大部分时候都不需要用到锁。只有在size 等操作时才需要锁住整个hash 表。



