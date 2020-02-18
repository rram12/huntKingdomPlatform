<?php

namespace ProductBundle\Controller;

use ProductBundle\Entity\Panier;
use ProductBundle\Entity\Produit;
use ProductBundle\Form\ProduitType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;

class ProductController extends Controller
{
    public function readAction()
    {

        $produit=$this->getDoctrine()->getRepository(Produit::class)->findAll();
        return $this->render('@Product/Product/read.html.twig', array(
            'produit'=>$produit
        ));
    }

    public function createAction(Request $request)
    {
        $produit=new Produit();
        $form=$this->createForm(ProduitType::class,$produit);
        $form=$form->handleRequest($request);
        if($form->isValid())
        {
            $file = $produit->getImage();
            $fileName = md5(uniqid()).'.'.$file->guessExtension();
            $file->move($this->getParameter('photos_directory'), $fileName);
            $produit->setImage($fileName);
            $em=$this->getDoctrine()->getManager();
            $em->persist($produit);
            $em->flush();
            return $this->redirectToRoute('readpr');
        }
        return $this->render('@Product/Product/create.html.twig', array(
            'form'=>$form->createView()
        ));
    }

    public function deleteAction($id)
    {
        $em=$this->getDoctrine()->getManager();
        $produit=$em->getRepository(Produit::class)->find($id);
        $em->remove($produit);
        $em->flush();
        return $this->redirectToRoute('readpr');
    }

    public function updateAction(Request $request,$id)
    {
        $produit = $this->getDoctrine()->getRepository('ProductBundle:Produit')->find($id);
        $form = $this->createForm(ProduitType::class, $produit);
        $form->remove('Ajouter');
        $form->add('Modify', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $produit = $form->getData();
            $em = $this->getDoctrine()->getManager();
            $em->persist($produit);
            $em->flush();
            return $this->redirectToRoute('readpr');
        }
        return $this->render('@Product/Product/update.html.twig', array(
            'form' => $form->createView()
        ));
    }


}
