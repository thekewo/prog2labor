#ifndef CLASS STUDENT_H
#define CLASS STUDENT_H

class Class_Student {

private:
	int StudentID;
	int FirstName;
	int LastName;
	int attribute;

public:
	void getStudentID();

	void setStudentID(int StudentID);

	void getFirstName();

	void setFirstName(int FirstName);

	void getLastName();

	void setLastName(int LastName);

	void getAttribute();

	void setAttribute(int attribute);
};

#endif
