<?php

namespace ForumBundle\Controller;

use ForumBundle\Entity\Publication;
use ForumBundle\Entity\Reclamation;
use ForumBundle\Form\PublicationType;
use ForumBundle\Form\ReclamationType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;

class ReclamationController extends Controller
{
    public function addRecAction(Request $request)
    {
        $rec= new Reclamation();
        $form= $this->createForm(ReclamationType::class,$rec);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            $rec->setDateRec(new \DateTime("now"));
            $rec->setHandled(false);
            $rec->user = $this->getUser();
            $em = $this->getDoctrine()->getManager();
            $em->persist($rec);
            $em->flush();
        //    return $this->redirectToRoute("list_complaint");
        }
        return $this->render("@Forum/dashboard/addRec.html.twig",array('Form'=>$form->createView()));
    }

    public function addRecFrontAction(Request $request)
    {
        $rec= new Reclamation();
        $form= $this->createForm(ReclamationType::class,$rec);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            $rec->setDateRec(new \DateTime("now"));
            $rec->setHandled(false);
            $rec->user = $this->getUser();
            $em = $this->getDoctrine()->getManager();
            $em->persist($rec);
            $em->flush();
            return $this->redirectToRoute("list_complaint");
        }
        return $this->render("@Forum/front/addRec.html.twig",array('Form'=>$form->createView()));
    }

    public function listRecAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $recs=$em->getRepository('ForumBundle:Reclamation')->findAll();
        return $this->render('@Forum/dashboard/reclamation.html.twig', array(
            "recs" =>$recs
        ));
    }
    public function recListAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $thisUser = $this->getUser();
        $recs=$em->getRepository('ForumBundle:Reclamation')->findByUser($thisUser);
        return $this->render('@Forum/front/blog-single.html.twig', array(
            "recs" =>$recs , "thisUser"=>$thisUser
        ));
    }

    public function updatePubAction(Request $request, $id)
    {
        $em=$this->getDoctrine()->getManager();
        $p= $em->getRepository('ForumBundle:Publication')->find($id);
        $form=$this->createForm(PublicationType::class,$p);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            if ($p->getImage() !== null){
                $file = $p->getImage();
                $filename = md5(uniqid()). '.' . $file->guessExtension();
                $file->move($this->getParameter('media_directory'), $filename);
                $p->setImage($filename);
            }
            else{
                $p->setImage(' ');
            }
            $p->setPublishedAt(new \DateTime('now'));
            $em= $this->getDoctrine()->getManager();
            $em->persist($p);
            $em->flush();
            return $this->redirectToRoute('list_publication');

        }
        return $this->render('@Forum/dashboard/updatePub.html.twig', array(
            "Form"=> $form->createView()
        ));
    }

    public function deletePubAction(Request $request)
    {
        $id = $request->get('id');
        $em= $this->getDoctrine()->getManager();
        $Post=$em->getRepository('ForumBundle:Publication')->find($id);
        $em->remove($Post);
        $em->flush();
        return $this->redirectToRoute('list_publication');
    }
    public function updateRecFrontAction(Request $request, $id)
    {
        $em=$this->getDoctrine()->getManager();
        $r= $em->getRepository('ForumBundle:Reclamation')->find($id);
        $form=$this->createForm(ReclamationType::class,$r);
        $form->handleRequest($request);
        if($form->isSubmitted()){
            $r->setDateRec(new \DateTime('now'));
            $em= $this->getDoctrine()->getManager();
            $em->persist($r);
            $em->flush();
            return $this->redirectToRoute('list_complaint');

        }
        return $this->render('@Forum/front/addRec.html.twig', array(
            "Form"=> $form->createView()
        ));
    }

    public function deleteRecFrontAction(Request $request)
    {
        $id = $request->get('id');
        $em= $this->getDoctrine()->getManager();
        $rec=$em->getRepository('ForumBundle:Reclamation')->find($id);
        $em->remove($rec);
        $em->flush();
        return $this->redirectToRoute('list_complaint');
    }

    public function handledFrontAction(Request $request)
    {
        $id = $request->get('id');
        $em= $this->getDoctrine()->getManager();
        $rec=$em->getRepository('ForumBundle:Reclamation')->find($id);
        $rec->setHandled(true);
        $em->flush();
        return $this->redirectToRoute('list_complaint');
    }

    public function handledAction(Request $request)
    {
        $id = $request->get('id');
        $em= $this->getDoctrine()->getManager();
        $rec=$em->getRepository('ForumBundle:Reclamation')->find($id);
        $rec->setHandled(true);
        $em->flush();
        return $this->redirectToRoute('list_complaint2');
    }

}
