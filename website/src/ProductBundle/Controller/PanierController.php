<?php

namespace ProductBundle\Controller;

use ProductBundle\Entity\Panier;
use ProductBundle\Form\PanierType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;

class PanierController extends Controller
{
    private $panier;

    function __construct(){
        $this->panier = new Panier();
        $date= new \DateTime();
        $this->panier->setDatePanier($date);
    }
    public function readAction()

    {

        $panier=$this->getDoctrine()->getRepository(Panier::class)->findAll();
        return $this->render('@Product/Panier/read.html.twig', array(
            'panier'=>$panier

        ));
    }

    public function createAction(Request $request)
    {
        $panier=new Panier();
        $form=$this->createForm(PanierType::class,$panier);
        $form=$form->handleRequest($request);
        if($form->isValid())
        {
            $em=$this->getDoctrine()->getManager();
            $em->persist($panier);
            $em->flush();
            return $this->redirectToRoute('readp');
        }
        return $this->render('@Product/Panier/create.html.twig', array(
            'form'=>$form->createView()
        ));
    }

    public function deleteAction($id)
    {
        $em=$this->getDoctrine()->getManager();
        $panier=$em->getRepository(Panier::class)->find($id);
        $em->remove($panier);
        $em->flush();
        return $this->redirectToRoute('readp');
    }

    public function updateAction(Request $request,$id)
    {
        $panier = $this->getDoctrine()->getRepository('ProductBundle:Panier')->find($id);
        $form = $this->createForm(PanierType::class, $panier);
        $form->remove('Ajouter');
        $form->add('Modify', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $club = $form->getData();
            $em = $this->getDoctrine()->getManager();
            $em->persist($panier);
            $em->flush();
            return $this->redirectToRoute('readp');
        }
        return $this->render('@Product/Panier/update.html.twig', array(
            'form' => $form->createView()
        ));
    }

    public function addtopanierAction(Request $request,$id)
    {
        $produit = $this->getDoctrine()->getRepository('ProductBundle:Produit')->find($id);
        $produits= $this->getDoctrine()->getRepository('ProductBundle:Produit')->findAll();

        $this->panier->addtopanier($produit);
        $this->panier->setPrixTotal($this->panier->getPrixTotal()+$produit->getPrix()*$produit->getQteProd() );
        $this->panier->setNbArticles($this->panier->getNbArticles()+ $produit->getQteProd());
        $em = $this->getDoctrine()->getManager();
        $em->persist($this->panier);
        $em->flush();



        return $this->render('@Product/Product/read.html.twig', array(
            'produit'=>$produits
        ));    }

}
