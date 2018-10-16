struct A
{
    int n;
    A(int n = 1) : n(n) { }
    A(const A& a) : n(a.n) { } // user-defined copy ctor
};
 
struct B : A
{
    // implicit default ctor B::B()
    // implicit copy ctor B::B(const B&)
};
 
struct C : B
{
     C() : B() { }
 private:
     C(const C&); // non-copyable, C++98 style
};
 
int main()
{
    A a1(7);
    A a2(a1); // calls the copy ctor
    B b;
    B b2 = b;
    A a3 = b; // conversion to A& and copy ctor
    volatile A va(10);
    // A a4 = va; // compile error
 
    C c;
    // C c2 = c; // compile error
}