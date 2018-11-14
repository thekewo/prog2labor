#include <iostream>
#include <vector>

class Person;
class Company;

class JobParameters{
    float salary;
    std::string jobTitle;
public:
    Person* person;
    Company* company;
};

class Person{
    std::vector<JobParameters*> jobParameters;
public:
    std::vector<JobParameters*> getJobParameters(){return jobParameters;}
    std::vector<Company*> getEmployers(){
        std::vector<Company*> employers;
        employers.reserve(jobParameters.size());
        for(std::vector<JobParameters*>::iterator it = jobParameters.begin(); it!= jobParameters.end(); it++){
            employers.push_back((*it)->company);
        }
        return employers;
    }
};