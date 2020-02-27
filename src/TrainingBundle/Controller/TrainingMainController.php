<?php

namespace TrainingBundle\Controller;


use DateTime;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use TrainingBundle\Entity\Entrainement;
use TrainingBundle\Form\EntrainementType;



class TrainingMainController extends Controller
{
    public function CreateAction(Request $request)
    {
        $entrainement=new Entrainement();
        $form=$this->createForm(EntrainementType::class,$entrainement);
        $form->handleRequest($request);
        $em=$this->getDoctrine()->getManager();
        $animals= $em->getRepository("TrainingBundle:Animal")->findAnimal();
        $products= $em->getRepository("ProductBundle:Produit")->findProductName();
        $namesAnimal=$request->get('animaux');
        $namesProduct=$request->get('products');
        $animalId=$em->getRepository("TrainingBundle:Animal")->findIdAnimal($namesAnimal);
        $productId=$em->getRepository("ProductBundle:Produit")->findIdProduct($namesProduct);
        $entrainementsAffichage= $em->getRepository("TrainingBundle:Entrainement")->findEntr($this->getUser());
        //$dateentr=$em->getRepository("TrainingBundle:Entrainement")->findEntrDate();
        //$output=array();
        $animalsMonth= $em->getRepository("TrainingBundle:Animal")->findAnimalMonth();

        $categorie=$request->get('categorie');

        if($form->isSubmitted()){
            $entrainement->setCategorie($categorie);
            $entrainement->setPrix(200);
            $entrainement->setAnimal($animalId[0]);
            $entrainement->setProduit($productId[0]);
            $entrainement->setUser($this->getUser());
            $entrainement->setAccepter("encours");
            $em->persist($entrainement);
            $em->flush();

        };
        return $this->render("@Training/front/create_training.html.twig",array('form'=>$form->createView(),'animals' => $animals,'products' => $products,'entrainementsAffichage' => $entrainementsAffichage,'animalsMonth'=>$animalsMonth));

    }


    function DeleteEntrainementAction($id)
    {
        $em=$this->getDoctrine()->getManager();
        $club=$em->getRepository(Entrainement::class)->find($id);
        $em->remove($club);
        $em->flush();
        return $this->redirectToRoute('training_front');
    }

    function DeleteEntrainementParEntraineurAction($id)
    {
        $em=$this->getDoctrine()->getManager();
        $club=$em->getRepository(Entrainement::class)->find($id);
        $em->remove($club);
        $em->flush();
        return $this->redirectToRoute('training_list');
    }


    public function listEntrainementAction()
    {
        if
        ($this->get('security.authorization_checker')->isGranted('ROLE_CLIENT')) {

            return $this->redirectToRoute('fos_user_security_login');
        }
        $em=$this->getDoctrine()->getManager();
        $entrainements= $em->getRepository("TrainingBundle:Entrainement")->findEntrainementNUll();
        $entrOui=$em->getRepository("TrainingBundle:Entrainement")->findEntrainementOui();
        return $this->render("@Training/front/list_entrainement.html.twig", array('entrOui'=>$entrOui,'entrainements' => $entrainements));

    }


    public function recupererEntraineurAction($id,Request $request)
    {

        $em=$this->getDoctrine()->getManager();

        $entrainement=$em->getRepository("TrainingBundle:Entrainement")->find($id);
        $entrainement->setEntraineur($this->getUser());
        $entr=$this->getUser()->getUsername();
        //$entrPass=$this->getUser()->getPassword();
        $entrainement->setAccepter("oui");
        $sujet="Réponse entrainement";
        $em->flush();
        $mailer = $this->container->get('mailer');
        $transport = \Swift_SmtpTransport::newInstance('smtp.gmail.com',465,'ssl')
            ->setUsername('salsabil.racil@esprit.tn')
            ->setPassword('58820591ssou');

        $mailer=\Swift_Mailer::newInstance($transport);
        $message=\Swift_Message::newInstance('')
            ->setSubject($sujet)
            ->setFrom('salsabil.racil@esprit.tn')
            ->setTo( $entrainement->getUser()->getEmail())
            ->setBody("l'entraineur ".$entr." a accepté votre demande d'entrainement ");
        $this->get('mailer')->send($message);
        return $this->redirectToRoute('training_list');

    }
}
