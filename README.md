# Naive-Bayes-classifier
Naive Bayes classifier is classification algorithm. It uses Naive based Bernoulli and Multinomial equation to classify documents(Text) as ham or spam.  Currently algorithm is only classifying in two categories but it can be modified to  classify a text in N categories.

<b>Project Structure</b><br/>
<b>/jars </b> executable jars if you don't want to run source code  <br/>
<b>/src/main </b> source code of Naive-Bayes-classifier  <br/>
<b>/src/corpus </b>  contains all data files trainining files and testing files, list of stop words file <br/>

you can run project by either importing it in eclipse or using cmd/termianl in java based environment <br/>

<h1> Using Eclipse </h1>
<ul>
  <li> Import Project in eclipse ( <i>using Existing Project into Workspace option</i> )</li>
  <li> run Train.java (main class) to train you corpus using <i>corpus_root_path</i> as run time argument </li>
  <li> After running Train.java check out <i>Bernouli.out</i> and <i>Multinomail.out</i> files at corpus_root_path. these are model build by bernouli and multinomial calculations</li>
  <li>Now run Test.java (main class ) with <i>corpus_root_path</i> and <i>path_of_file__you_want_to_classify as run time arguments</i>
  </li>
  <li>Test.java will classify your file with results of both Bernouli and Multinomial</li>

</ul>

<h1> Using Command Line </h1>
<small>command line option is easy </small>
<ul>
  <li>cd jars folder</li>
  <li> run command <b> java -jar train1.jar path_of_corpus_folder</b></li>
  <li> above command will create Bernouli and Multinomial models in corpus folder</li>
  <li> run command <b> java -jar test.jar path_of_corpus_folder &nbsp;&nbsp; path_of_file__you_want_to_classify </b></li>
  <li>above command will classify your file with results of both Bernouli and Multinomial</li>

</ul>

<br/>
<div>
  <b> Note </b> I will try to push regular updates on algorithm, in case you don't understand anything please hit me out at alihaider907@gmail.com or tweet me @alihaider907 <b>Thank you</b>
</div>