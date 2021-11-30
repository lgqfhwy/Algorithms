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
// newNodeOfLevel����һ��nodeStructure�ṹ�壬ͬʱ����l��node *����ָ��
#define newNodeOfLevel(l) (node)malloc(sizeof(struct nodeStructure)+(l)*sizeof(node *))

typedef int keyType;
typedef int valueType;

#ifdef allowDuplicates
boolean delete(), search();
void insert();
#else
boolean insert(), delete(), search();
#endif

// ���������һ��ָ��
typedef struct nodeStructure *node;

typedef struct nodeStructure
{
    keyType key;	// keyֵ
    valueType value;	// valueֵ
    // ��ǰָ�����飬���ݸýڵ������
    // ��ָͬ��ͬ��С������
    node forward[1];	
};

// ����������������
typedef struct listStructure{
   int level; 	  /* Maximum level of the list 
			(1 more than the number of levels in the list) */
   struct nodeStructure * header; /* pointer to header */
   } * list;

node NIL;

int randomsLeft;
int randomBits;

init() { 
	// ����һ���ڵ�
  NIL = newNodeOfLevel(0);
  NIL->key = 0x7fffffff;
  randomBits = random();
  randomsLeft = BitsInRandom/2;
};

/*
 ����һ���յ��������ݽṹ
 */
list newList()
{
  list l;
  int i;
	// ����list���ʹ�С���ڴ�
  l = (list)malloc(sizeof(struct listStructure));
  // ��������Ĳ�level����ʼ�Ĳ�Ϊ0�㣨�����0��ʼ��
  l->level = 0;
  
  // ����header��Ϣ����
  l->header = newNodeOfLevel(MaxNumberOfLevels);
  // ��header��forward�������
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
// ����һ���ڵ�
boolean insert(l,key,value) 
	register list l;
	register keyType key;
	register valueType value;
{
  register int k;
  // ʹ����update����
  node update[MaxNumberOfLevels];
  register node p,q;

  p = l->header;
  k = l->level;
  /*******************1*********************/
  do {
		// ���Ҳ���λ��
		while (q = p->forward[k], q->key < key)
			p = q;
		
		// ����update����
		update[k] = p;
	} while(--k>=0);	// ����ÿһ����б���
	
	// �����Ѿ����ҵ��˺��ʵ�λ�ã�����update�����Ѿ�
	// ������Ԫ��
  if (q->key == key)
  {
    q->value = value;
		return(false);
	};
	
	// �������һ������
  k = randomLevel();  
 	if (k>l->level) 
  {
  	// ��������ɵĲ���������Ĳ�����Ļ�
    // ������������Ĳ���
		k = ++l->level;
		// ��update�����н�����ӵĲ�ָ��l->header
		update[k] = l->header;
	};
		
	/*******************2*********************/
	// ���ɲ������ڵ���Ŀ
  q = newNodeOfLevel(k);
  q->key = key;
  q->value = value;
      
	// ��������ָ����
  do 
  {
		p = update[k];
		q->forward[k] = p->forward[k];
		p->forward[k] = q;
	} while(--k>=0);
	
	// ����������е���������Ѿ������˸ýڵ�

  return(true);
}
////////////////////////////////////////////////
// ɾ������
boolean delete(l,key) 
register list l;
register keyType key;
{
  register int k,m;
  // ����һ����������update
  node update[MaxNumberOfLevels];
  register node p,q;

  p = l->header;
  k = m = l->level;
  // ����Ͳ��Ҳ������ƣ�����update�а������ǣ�
  // ָ��ýڵ��Ӧ���ǰ���ڵ�
  do 
  {
		while (q = p->forward[k], q->key < key) 
			p = q;
			update[k] = p;
	} while(--k>=0);

	// ����ҵ��˸ýڵ㣬�Ž���ɾ���Ķ���
  if (q->key == key) 
  {
  	// ָ������
		for(k=0; k<=m && (p=update[k])->forward[k] == q; k++) 
			// ��������޸�l->header->forward�����ֵ�� 
		  p->forward[k] = q->forward[k];
		// �ͷ�ʵ���ڴ�
		free(q);
		
		// ���ɾ����������Ľڵ㣬��ô��Ҫ����ά�������
		// ����level
   	while( l->header->forward[m] == NIL && m > 0 )
	     m--;
		l->level = m;
		return(true);
	}
  else
  	// û���ҵ��ýڵ㣬������ɾ������ 
  	return(false);
}

//////////////////////////////////////////////////
// ����
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
  // ������ҵ���ֵ�Ǵ��ڻ����ǵ�����Ҫ���ҵ�keyֵ��
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
