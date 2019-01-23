package aiwd.artificialNeuralNetwork;

public class LearningResults {

    private double badResults = 0;
    private double allResults = 0;

    LearningResults() {
    }

    void addResult(){
        allResults +=1;
    }

    void addBadResult(){
        badResults +=1;
    }

    void clear(){
        allResults = 0;
        badResults = 0;
    }

    void setBadResults(long badResults) {
        this.badResults = badResults;
    }

    void setAllResults(long allResults) {
        this.allResults = allResults;
    }

    public double getResult(){
        if(allResults == 0 ) return 0;
        if(badResults == 0 ) return 100;
        return 100.0 - badResults/allResults * 100.0;
    }
}
