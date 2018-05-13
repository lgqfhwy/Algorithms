/* 

Example of Skip List source code for C:

Skip Lists are a probabilistic alternative to balanced trees, as
described in the June 1990 issue of CACM and were invented by 
William Pugh in 1987. 

This file contains source code to implement a dictionary using 
skip lists and a test driver to test the routines.

A couple of comments about this implementation:
  The routine randomLevel has been hard-coded to generate random
  levels using p=0.25. It can be easily changed.
  
  The insertion routine has been implemented so as to use the
  dirty hack described in the CACM paper: if a random level is
  generated that is more than the current maximum level, the
  current maximum level plus one is used instead.

  Levels start at zero and go up to MaxLevel (which is equal to
	(MaxNumberOfLevels-1).

The compile flag allowDuplicates determines whether or not duplicates
are allowed. If defined, duplicates are allowed and act in a FIFO manner.
If not defined, an insertion of a value already in the file updates the
previously existing binding.

BitsInRandom is defined to be the number of bits returned by a call to
random(). For most all machines with 32-bit integers, this is 31 bits
as currently set. 

The routines defined in this file are:

  init: defines NIL and initializes the random bit source 

  newList: returns a new, empty list 

  freeList(l): deallocates the list l (along with any elements in l)

  randomLevel: Returns a random level

  insert(l,key,value): inserts the binding (key,value) into l. If 
	allowDuplicates is undefined, returns true if key was newly 
	inserted into the list, false if key already existed

  delete(l,key): deletes any binding of key from the l. Returns
	false if key was not defined.

  search(l,key,&value): Searches for key in l and returns true if found.
	If found, the value associated with key is stored in the
	location pointed to by &value

*/


#define false 0
#define true 1
typedef char boolean;
#define BitsInRandom 31

#define allowDuplicates

#define MaxNumberOfLevels 16
#define MaxLevel (MaxNumberOfLevels-1)
// newNodeOfLevel生成一个nodeStructure结构体，同时生成l个node *数组指针
#define newNodeOfLevel(l) (node)malloc(sizeof(struct nodeStructure)+(l)*sizeof(node *))

typedef int keyType;
typedef int valueType;

#ifdef allowDuplicates
boolean delete(), search();
void insert();
#else
boolean insert(), delete(), search();
#endif

// 这里仅仅是一个指针
typedef struct nodeStructure *node;

typedef struct nodeStructure
{
    keyType key;	// key值
    valueType value;	// value值
    // 向前指针数组，根据该节点层数的
    // 不同指向不同大小的数组
    node forward[1];	
};

// 定义跳表数据类型
typedef struct listStructure{
   int level; 	  /* Maximum level of the list 
			(1 more than the number of levels in the list) */
   struct nodeStructure * header; /* pointer to header */
   } * list;

node NIL;

int randomsLeft;
int randomBits;

init() { 
	// 生成一个节点
  NIL = newNodeOfLevel(0);
  NIL->key = 0x7fffffff;
  randomBits = random();
  randomsLeft = BitsInRandom/2;
};

/*
 生成一个空的跳表数据结构
 */
list newList()
{
  list l;
  int i;
	// 申请list类型大小的内存
  l = (list)malloc(sizeof(struct listStructure));
  // 设置跳表的层level，初始的层为0层（数组从0开始）
  l->level = 0;
  
  // 生成header信息部分
  l->header = newNodeOfLevel(MaxNumberOfLevels);
  // 将header的forward数组清空
  for(i=0;i<MaxNumberOfLevels;i++) l->header->forward[i] = NIL;
  return(l);
}; 

freeList(l) 
  list l; 
  {
  register node p,q;
  p = l->header;
  do {
    q = p->forward[0];
    free(p);
    p = q; }
    while (p!=NIL);
  free(l);
  };

int randomLevel()
  {register int level = 0;
   register int b;
   do {
    b = randomBits&3;
    if (!b) level++;
    randomBits>>=2;
    if (--randomsLeft == 0) {
        randomBits = random();
        randomsLeft = BitsInRandom/2;
        };
    } while (!b);
    return(level>MaxLevel ? MaxLevel : level);
    };

////////////////////////////////////////////////////
// 插入一个节点
boolean insert(l,key,value) 
	register list l;
	register keyType key;
	register valueType value;
{
  register int k;
  // 使用了update数组
  node update[MaxNumberOfLevels];
  register node p,q;

  p = l->header;
  k = l->level;
  /*******************1*********************/
  do {
		// 查找插入位置
		while (q = p->forward[k], q->key < key)
			p = q;
		
		// 设置update数组
		update[k] = p;
	} while(--k>=0);	// 对于每一层进行遍历
	
	// 这里已经查找到了合适的位置，并且update数组已经
	// 填充好了元素
  if (q->key == key)
  {
    q->value = value;
		return(false);
	};
	
	// 随机生成一个层数
  k = randomLevel();  
 	if (k>l->level) 
  {
  	// 如果新生成的层数比跳表的层数大的话
    // 增加整个跳表的层数
		k = ++l->level;
		// 在update数组中将新添加的层指向l->header
		update[k] = l->header;
	};
		
	/*******************2*********************/
	// 生成层数个节点数目
  q = newNodeOfLevel(k);
  q->key = key;
  q->value = value;
      
	// 更新两个指针域
  do 
  {
		p = update[k];
		q->forward[k] = p->forward[k];
		p->forward[k] = q;
	} while(--k>=0);
	
	// 如果程序运行到这里，程序已经插入了该节点

  return(true);
}
////////////////////////////////////////////////
// 删除函数
boolean delete(l,key) 
register list l;
register keyType key;
{
  register int k,m;
  // 生成一个辅助数组update
  node update[MaxNumberOfLevels];
  register node p,q;

  p = l->header;
  k = m = l->level;
  // 这里和查找部分类似，最终update中包含的是：
  // 指向该节点对应层的前驱节点
  do 
  {
		while (q = p->forward[k], q->key < key) 
			p = q;
			update[k] = p;
	} while(--k>=0);

	// 如果找到了该节点，才进行删除的动作
  if (q->key == key) 
  {
  	// 指针运算
		for(k=0; k<=m && (p=update[k])->forward[k] == q; k++) 
			// 这里可能修改l->header->forward数组的值的 
		  p->forward[k] = q->forward[k];
		// 释放实际内存
		free(q);
		
		// 如果删除的是最大层的节点，那么需要重新维护跳表的
		// 层数level
   	while( l->header->forward[m] == NIL && m > 0 )
	     m--;
		l->level = m;
		return(true);
	}
  else
  	// 没有找到该节点，不进行删除动作 
  	return(false);
}

//////////////////////////////////////////////////
// 查找
boolean search(l,key,valuePointer)
register list l;
register keyType key;
valueType * valuePointer;
  {
  register int k;
  register node p,q;
  p = l->header;
  k = l->level;
  
  
  do 
  {
  	while (q = p->forward[k], q->key < key) 
  		p = q;
  }
  while (--k>=0);
  // 这里查找到的值是大于或者是等于需要查找的key值的
  if (q->key != key) return(false);
  *valuePointer = q->value;
  return(true);
  };

#define sampleSize 65536
main() {
  list l;
  register int i,k;
  keyType keys[sampleSize];
  valueType v;

  init();

  l= newList();

  for(k=0;k<sampleSize;k++) {
        keys[k]=random();
        insert(l,keys[k],keys[k]);
        };


  for(i=0;i<4;i++) {
        for(k=0;k<sampleSize;k++) {
	    if (!search(l,keys[k],&v)) printf("error in search #%d,#%d\n",i,k);
	    if (v != keys[k]) printf("search returned wrong value\n");
            };
        for(k=0;k<sampleSize;k++) {
	    if (! delete(l,keys[k])) printf("error in delete\n");
            keys[k] = random();
	    insert(l,keys[k],keys[k]);
            };
        };

  freeList(l);

  };
