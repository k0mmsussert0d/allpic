import React from "react";
import { Input, Help } from "rbx";
import {FieldError, useController, UseControllerOptions} from "react-hook-form";

export interface ControlledInputProps extends UseControllerOptions {
  type?: "number" | "time" | "text" | "color" | "date" | "search" | "email" | "tel" | "password" | undefined,
  placeholder?: string
  error?: FieldError | undefined
}

const ControlledInput = (props: ControlledInputProps) => {
  const { field, meta } = useController(props);

  return (
    <>
    <Input
      {...field}
      placeholder={props.placeholder ?? ''}
      type={props.type ?? 'text'}
      color={meta.invalid ? 'danger' : undefined}
    />
      {props.error && <Help color='danger'>{props.error.message}</Help>}
    </>
  );
}

export default ControlledInput;
