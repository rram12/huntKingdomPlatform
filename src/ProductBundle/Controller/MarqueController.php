<?php

namespace ProductBundle\Controller;

use ProductBundle\Entity\Marque;
use ProductBundle\Form\MarqueType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;

class MarqueController extends Controller
{
    public function readAction()
    {
        $marque=$this->getDoctrine()->getRepository(Marque::class)->findAll();
        return $this->render('@Product/Marque/read.html.twig', array(
            'marque'=>$marque
        ));
    }

    public function createAction(Request $request)
    {
        $marque=new Marque();
        $form=$this->createForm(MarqueType::class,$marque);
        $form=$form->handleRequest($request);
        if($form->isValid())
        {
            $em=$this->getDoctrine()->getManager();
            $em->persist($marque);
            $em->flush();
            return $this->redirectToRoute('read');
        }
        return $this->render('@Product/Marque/create.html.twig', array(
            'form'=>$form->createView()
        ));
    }

    public function updateAction(Request $request,$id)
    {

        $marque = $this->getDoctrine()->getRepository('ProductBundle:Marque')->find($id);
        $form = $this->createForm(MarqueType::class, $marque);
        $form->remove('Ajouter');
        $form->add('Modifier', SubmitType::class);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $marque = $form->getData();
            $em = $this->getDoctrine()->getManager();
            $em->persist($marque);
            $em->flush();
            return $this->redirectToRoute('readm');
        }
        return $this->render('@Product/Marque/update.html.twig', array(
            'form' => $form->createView()
        ));
    }

    public function deleteAction($id)
    {
        $em=$this->getDoctrine()->getManager();
        $marque=$em->getRepository(Marque::class)->find($id);
        $em->remove($marque);
        $em->flush();
        return $this->redirectToRoute('readm');
    }
}
