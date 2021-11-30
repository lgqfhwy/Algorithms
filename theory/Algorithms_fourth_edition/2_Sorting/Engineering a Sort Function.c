//Engineering a Sort Function

// iisort, the first i stands for 'integer', the second for 'insertion'.
// The algorithm varies index i from the second through the last element
// of the array, and varies j to sift the j-th element down to its proper
// place in the preceding subarray.
void iisort(int *a, int n) {
    int i, j;
    for (i = 1; i < n; i++) {
        for (j = i; j > 0 && a[j - 1] > a[j]; j--) {
            iswap(j, j - 1, a); 
            // The function iswap(i, j, a) exchanges the integers a[i] and a[j].
        }
    }
}

// This iisort function sorts only integers. For the moment, we take the general 
// qsort interface to be:
//          void qsort(char *a, int n, int es, int (*cmp)());
// The first parameter points to the array to be sorted. The next two parameters tell
// the number of elements and the element size in bytes. The last parameter is a comparison
// function that takes two pointer arguments. The function returns an integer that is less than,
// equal to, or greater than the second. 

// Here is a typical comparison function and a sample call to sort an array of 
// non-negative integers.
// Program 1. A simple swap function
void swap(char *i, chat *j, int n) {
    do {
        char c = *i;
        *i++ = *j;
        *j++ = c;
    } while (--n > 0);
}

int intcomp(int *i, int *j) {
    return *i - *j;
}

qsort((char *)a, n, sizeof(int), intcomp);

// To sort an array of len-byte strings with terminal null characters,
// use the standard string-comparison routine, strcmp:
qsort(a, n, len, strcmp);

// To sort an array of pointers to strings, use strcmp with another level of indirection.
int pstrcmp(char **i, char **j) {
    return strcmp(*i, *j);
}

qsort(a, n, sizeof(char *), pstrcmp);

//By straightforward rewriting we convert iisort to handle a qsort-like interface.
// The function swap(i, j, n), defined in Program 1, interchanges n-byte fields pointed to
// by i and j.
void isort(char *a, int n, int es, int (*cmp)()) {
    char *pi, *pj;
    for (pi = a + es; pi < a + n * es; pi += es) {
        for (pj = pi; pj > a && cmp(pj - es, pj) > 0; pj -= es)
            swap(pj, pj - es, es);
    }
}


// Program 2 is a trivial Quicksort, which uses a partitioning scheme due to Lomuto. This
// code partitions around the first element in the array, which is finally placed in a[j].
// To sort the left subarray a[0..j-1], we call iqsort0(a, j). To sort the right subarray
// a[j+1..n-1], we use C pointer arithmetic and call iqsort0(a+j+1, n-j-1).

// Program 2. A toy Quicksort, unfit for general use
void iqsort0(int *a, int n) {
    int i, j;
    if (n <= 1)
        return;
    for (i = 1, j = 0; i < n; i++) {
        if (a[i] < a[0])
            swap(++j, i, a);
    }
    swap(0, j, a);
    iqsort0(a, j);
    iqsort0(a+j+1, n-j-1);
}


// A more efficient (and more familiar) partitioning method uses two indexes i and j.
// Index i scans up from the bottom of the array until it reaches a large element (greater than
// or equal to the partition value), and j scans down until it reaches a samll element. The two
// array elements are then swapped, and the scans continue until the pointers cross.

// Program 3. A simple Quicksort for integers
void iqsort1(int *a, int n) {
    int i, j;
    if (n <= 1)
        return;
    i = rand() % n;
    swap(0, i, a);
    i = 0;
    j = n;
    for (;;) {
        do {
            i++;
        } while (i < n && a[i] < a[0]);
        do {
            j--;
        } while (a[j] > a[0]);
        if (j < i)
            break;
        swap(i, j, a);
    }
    swap(0, j, a);
    iqsort1(a, j);
    iqsort1(a+j+1, n-j-1);
}

// One such elaboration is Program 4, which supports the qsort interface.
// Program 4. A simple qsort
void qsort1(char *a, int n, int es, int (*cmp)()) {
    int j;
    char *pi, *pj, *pn;
    if (n <= 1)
        return;
    pi = a + (rand() % n) * es;
    swap(a, pi, es);
    pi = a;
    pj = pn = a + n * es;
    for (;;) {
        do {
            pi += es;
        } while (pi < pn && cmp(pi, a) < 0);
        do {
            pj -= es;
        } while (cmp(pj, a) > 0);
        if (pj < pi)
            break;
        swap(pi, pj, es);
    }
    swap(a, pj, es);
    j = (pj - a) / es;
    qsort1(a, j, es, cmp);
    qsort1(a+(j+1)*es, n-j-1, es, cmp);
}

// Program 5 finds the median of three elements, using the qsort comparison function.
// It takes two or three comparisons (8/3 on average) and no swaps to evaluate the decision tree shown.

// Program 5. Decision tree and program for median of three
static char *med3(char *a, char *b, char *c, int (*cmp)()) {
    return cmp(a, b) < 0 ?
         (cmp(b, c) < 0 ? b : cmp(a, c) < 0 ? c : a)
        :(cmp(b, c) > 0 ? b : cmp(a, c) > 0 ? c : a);
}


// Program 6. An integer qsort with split-end partitioning
void iqsort2(int *x, int n) {
    int a, b, c, d, l, h, s, v;
    if (n <= 1) return;
    v = x[rand() % n];
    a = b = 0;
    c = d = n - 1;
    for (;;) {
        while (b <= c && x[b] <= v) {
            if (x[b] == v)
                iswap(a++, b, x);
            b++;
        }
        while (c >= b && x[c] >= v) {
            if (x[c] == v)
                iswap(d--, c, x);
            c--;
        }
        if (b > c)
            break;
        iswap(b++, c--, x);
    }
    s = min(a, b-a);
    for (l = 0, h = b-s; s; s--)
        iswap(l++, h++, x);
    s = min(d-c, n-1-d);
    for (l = b, h = n-s; s; s--)
        iswap(l++, h++, x);
    iqsort2(x, b-a);
    iqsort2(x+n-(d-c), d-c);
}

// Program 7. The final qsort; see Appendix for macro and type definitions
void qsort(char *a, size_t n, size_t es, int (*cmp)()) {
    char *pa, *pb, *pc, *pd, *pl, *pm, *pn, *pv;
    int r, swaptype;
    WORD t, v;
    size_t s;
    SWAPINIT(a, es);
    if (n < 7) {    // Insertion sort on smallest arrays
        for (pm = a + es; pm < a + n * es; pm += es) {
            for (pl = pm; pl > a && cmp(pl-es, pl) > 0; pl -= es)
                swap(pl, pl-es);
        }
        return;
    }
    pm = a + (n / 2) * es;  // Small arrays, middle element
    if (n > 7) {
        pl = a;
        pn = a + (n - 1) * es;
        if (n > 40) {   // Big arrays, pseudomedian of 9
            s = (n / 8) * es;
            pl = med3(pl, pl+s, pl+2*s, cmp);
            pm = med3(pm-s, pm, pm+s, cmp);
            pn = med3(pn-2*s, pn-s, pn, cmp);
        }
        pm = med3(pl, pm, pn, cmp);    // Mid-size, med of 3
    }
    PVINIT(pv, pm);    // pv points to partition value
    pa = pb = a;
    pc = pd = a + (n-1) * es;
    for (;;) {
        while (pb <= pc && (r = cmp(pb, pv)) <= 0) {
            if (r == 0) {
                swap(pa, pb);
                pa += es;
            }
            pb += es;
        }
        while (pc >= pb && (r = cmp(pc, pv)) >= 0) {
            if (r == 0) {
                swap(pc, pd);
                pd -= es;
            }
            pc -= es;
        }
        if (pb > pc)
            break;
        swap(pb, pc);
        pb += es;
        pc -= es;
    }
    pn = a + n * es;
    s = min(pa-a, pb-pa);
    vecswap(a, pb-s, s);
    s = min(pd-pc, pn-pd-es);
    vecswap(pb, pn-s, s);
    if ((s = pb-pa) > es)
        qsort(a, s/es, es, cmp);
    if ((s = pd-pc) > es)
        qsort(pn-s, s/es, es, cmp);
}

typedef long WORD;
#define W sizeof(WORD)  // must be a power of 2
#define SWAPINIT(a, es) swaptype = (a-(char*)0 | es) % W ? 2 : es > W ? 1 : 0
#define exch(a, b, t)  (t = a, a = b, b = t)
#define swap(a, b)  \
    swaptype != 0 ? swapfunc(a, b, es, swaptype) : \
    (void)exch(*(WORD*)(a), *(WORD*)(b), t)
#define vecswap(a, b, n)  if (n > 0)  swapfunc(a, b, n, swaptype)
#include <stddef.h>
static void swapfunc(char *a, char *b, size_t n, int swaptype) {
    if (swaptype <= 1) {
        WORD t;
        for ( ; n > 0; a += W, b += W, n -= W)
            exch(*(WORD*)a, *(WORD*)b, t);
    }
    else {
        char t;
        for ( ; n > 0; a += 1, b += 1, n -= 1)
            exch(*a, *b, t);
    }
}

#define PVINIT(pv, pm) \
    if (swaptype != 0) pv = a, swap(pv, pm); \
    else pv = (char*)&v, v = *(WORD*)pm















































