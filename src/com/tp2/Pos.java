package com.tp2;

import java.io.*;
import java.nio.charset.StandardCharsets;

import opennlp.tools.ml.TrainerFactory;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.*;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.*;
import opennlp.tools.util.model.ModelType;

public class Pos {

    public static void main(String[] args) {

        // Prints "Hello, World" to the terminal window.

        int iterations = 100;

        System.out.println("Cria modelos");
        System.out.println("Maxent");
        maxent(iterations);
        System.out.println("Perceptron");
        perceptron(iterations);
        System.out.println("Perceptron-Sequence");
        perceptronSequence(iterations);


        System.out.println("Rotula");
        System.out.println("Maxent");
        maxentTagger();
        System.out.println("Perceptron");
        perceptronTagger();
        System.out.println("Perceptron-Sequence");
        perceptronSequTagger();


    }

    public static void maxent(int iterations) {

        InputStream tokenModelIn = null;
        InputStream posModelIn = null;

        POSModel model = null;

        try {
            InputStreamFactory dataIn = new InputStreamFactory() {
                public InputStream createInputStream() throws IOException {
                    return new FileInputStream("C:\\Users\\Lucas\\Documents\\git\\opennlptp2\\src\\com\\tp2\\mac\\macmorpho-train.txt");
                }
            };

            ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, StandardCharsets.UTF_8);
            ObjectStream<POSSample> sampleStream = new WordTagSampleStream(lineStream);

            System.out.println("Oi");
            System.out.println(sampleStream.read());

            TrainingParameters trainParams = new TrainingParameters();
            trainParams.put(TrainingParameters.ALGORITHM_PARAM, ModelType.MAXENT.name());
            trainParams.put(TrainingParameters.ITERATIONS_PARAM, iterations);
            //trainParams.put(TrainingParameters. iterations);

            POSTaggerFactory posTaggerFactory = new POSTaggerFactory();

            model = POSTaggerME.train("pt", sampleStream, trainParams, posTaggerFactory);

            OutputStream modelOut = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Lucas\\Documents\\git\\opennlptp2\\src\\com\\tp2\\model\\maxent.model"));
            model.serialize(modelOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void perceptron(int iterations) {

        InputStream tokenModelIn = null;
        InputStream posModelIn = null;

        POSModel model = null;

        try {
            InputStreamFactory dataIn = new InputStreamFactory() {
                public InputStream createInputStream() throws IOException {
                    return new FileInputStream("C:\\Users\\Lucas\\Documents\\git\\opennlptp2\\src\\com\\tp2\\mac\\macmorpho-train.txt");
                }
            };

            ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, StandardCharsets.UTF_8);
            ObjectStream<POSSample> sampleStream = new WordTagSampleStream(lineStream);

            System.out.println("Oi");
            System.out.println(sampleStream.read());

            TrainingParameters trainParams = new TrainingParameters();
            trainParams.put(TrainingParameters.ALGORITHM_PARAM, ModelType.PERCEPTRON.name());
            trainParams.put(TrainingParameters.ITERATIONS_PARAM, iterations);

            POSTaggerFactory posTaggerFactory = new POSTaggerFactory();

            model = POSTaggerME.train("pt", sampleStream, trainParams, posTaggerFactory);

            OutputStream modelOut = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Lucas\\Documents\\git\\opennlptp2\\src\\com\\tp2\\model\\perceptron.model"));
            model.serialize(modelOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void perceptronSequence(int iterations) {

        InputStream tokenModelIn = null;
        InputStream posModelIn = null;

        POSModel model = null;

        try {
            InputStreamFactory dataIn = new InputStreamFactory() {
                public InputStream createInputStream() throws IOException {
                    return new FileInputStream("C:\\Users\\Lucas\\Documents\\git\\opennlptp2\\src\\com\\tp2\\mac\\macmorpho-train.txt");
                }
            };

            ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, StandardCharsets.UTF_8);
            ObjectStream<POSSample> sampleStream = new WordTagSampleStream(lineStream);

            System.out.println("Oi");
            System.out.println(sampleStream.read());

            TrainingParameters trainParams = new TrainingParameters();
            trainParams.put(TrainingParameters.TRAINER_TYPE_PARAM	, ModelType.PERCEPTRON_SEQUENCE.name());
            trainParams.put(TrainingParameters.ITERATIONS_PARAM, iterations);

            POSTaggerFactory posTaggerFactory = new POSTaggerFactory();

            model = POSTaggerME.train("pt", sampleStream, trainParams, posTaggerFactory);

            OutputStream modelOut = new BufferedOutputStream(new FileOutputStream("C:\\Users\\Lucas\\Documents\\git\\opennlptp2\\src\\com\\tp2\\model\\perceptronsequ.model"));
            model.serialize(modelOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void maxentTagger() {

        InputStream modelIn = null;

        try {
            modelIn = new FileInputStream("C:\\Users\\Lucas\\Documents\\git\\opennlptp2\\src\\com\\tp2\\model\\maxent.model");
            POSModel model = new POSModel(modelIn);

            POSTaggerME tagger = new POSTaggerME(model);

            String sent[] = new String[]{"Um", "gato", "cachorro", "pessoa", "ver", "comprar", "bonito",
                    "caro", "Jorge", "jornal", "dia", "."};
            String tags[] = tagger.tag(sent);

            double probs[] = tagger.probs();

            Sequence topSequences[] = tagger.topKSequences(sent);

            //print titles with probabilities
            String msg = "";
            for( int i = 0; i<topSequences.length; i++) {
                msg = "" + topSequences[i];
                System.out.println(msg);
            }

            //System.out.println(topSequences[0]);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void perceptronTagger() {

        InputStream modelIn = null;

        try {
            modelIn = new FileInputStream("C:\\Users\\Lucas\\Documents\\git\\opennlptp2\\src\\com\\tp2\\model\\perceptron.model");
            POSModel model = new POSModel(modelIn);

            POSTaggerME tagger = new POSTaggerME(model);

            String sent[] = new String[]{"Um", "gato", "cachorro", "pessoa", "ver", "comprar", "bonito",
                    "caro", "Jorge", "jornal", "dia", "."};
            String tags[] = tagger.tag(sent);

            double probs[] = tagger.probs();

            Sequence topSequences[] = tagger.topKSequences(sent);

            //print titles with probabilities
            String msg = "";
            for( int i = 0; i<topSequences.length; i++) {
                msg = "" + topSequences[i];
                System.out.println(msg);
            }

            //System.out.println(topSequences[0]);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void perceptronSequTagger() {

        InputStream modelIn = null;

        try {
            modelIn = new FileInputStream("C:\\Users\\Lucas\\Documents\\git\\opennlptp2\\src\\com\\tp2\\model\\perceptronsequ.model");
            POSModel model = new POSModel(modelIn);

            POSTaggerME tagger = new POSTaggerME(model);

            String sent[] = new String[]{"Um", "gato", "cachorro", "pessoa", "ver", "comprar", "bonito",
                    "caro", "Jorge", "jornal", "dia", "."};
            String tags[] = tagger.tag(sent);

            double probs[] = tagger.probs();

            Sequence topSequences[] = tagger.topKSequences(sent);

            //print titles with probabilities
            String msg = "";
            for( int i = 0; i<topSequences.length; i++) {
                msg = "" + topSequences[i];
                System.out.println(msg);
            }

            //System.out.println(topSequences[0]);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
