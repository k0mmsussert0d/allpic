import {
  Media,
  Field,
  Control,
  Textarea,
  Level,
  Button,
} from "rbx";
import { useState } from "react";
import { APIMethods } from "../../../services/ApiActions";
import { APIResponse, CommentDTO } from "../../../types/API";
import './AddComment.scss';

const AddComment = ({ imageToken, callback }: AddCommentProps) => {

  const [text, setText] = useState('');
  const [submitting, setSubmitting] = useState(false);

  const handleTextChange = (event: React.ChangeEvent<HTMLTextAreaElement>): void => {
    setText(event.target.value);
  }

  const handleKeyPress = (event: React.KeyboardEvent<HTMLTextAreaElement>): void => {
    if (event.key === 'Enter' && event.ctrlKey) {
      submitComment();
    }
  }

  const submitComment = (): void => {
    const handleSubmission = async () => {
      if (!text) {
        return;
      }
      
      setSubmitting(true);
      APIMethods.postComment(imageToken, text)
        .then((res: APIResponse<CommentDTO>) => {
          setSubmitting(false);
          setText('');

          if (callback) {
            callback(res);
          }
        });
    };

    handleSubmission();
  }

  return (
    <Media>
      <Media.Item align="content">
        <Field>
          <Control>
            <Textarea placeholder="Add a comment..." value={text} onChange={handleTextChange} onKeyUp={handleKeyPress} />
          </Control>
        </Field>
        <Level>
          <Level.Item as="div" className="submit-button">
            {submitting ?
              <Button state="loading" color="info">Loading</Button> :
              <Button type="submit" color="info" onClick={submitComment}>Submit comment</Button>
            }
          </Level.Item>
        </Level>
      </Media.Item>
    </Media>
  );
};

export interface AddCommentProps {
  imageToken: string,
  callback?: (res: APIResponse<CommentDTO>) => void
}

export default AddComment;
